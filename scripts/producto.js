<<<<<<< HEAD
// Lista de productos y carrito
let productos = [];
let carrito = JSON.parse(localStorage.getItem('carrito')) || [];

// Cargar productos al iniciar la página
function cargarProductos() {
    fetch('producto.json')
        .then(res => res.json())
        .then(json => {
            productos = json; // Guardamos los productos en la variable
            const container = document.getElementById('productoContainer');
            container.innerHTML = '';
            productos.forEach(producto => {
                const div = document.createElement('div');
                div.innerHTML = `
                    <div class="producto">
                        <img class="img-producto" src="${producto.imagen}" alt="${producto.nombre}" style="height:300px; margin-top:20px;" onclick="verDetalle(${producto.id_producto})">
                        <h2 style="height:55px; overflow:hidden;">${producto.nombre}</h2>
                        <p><b>${producto.precio}€</b></p>
                        <button onclick="añadirAlCarrito(${producto.id_producto})">Añadir al carrito</button>
                    </div>
                    <br>
                `;
                container.appendChild(div);
            });
        })
        .catch(error => console.error('Error al cargar los productos:', error));
}

// Función para ver el detalle de un producto
function verDetalle(id) {
    let producto = productos.find(p => p.id_producto === id);
    if (!producto) {
        // Si no se encuentra, se vuelve a buscar en el JSON
        fetch('producto.json')
            .then(res => res.json())
            .then(data => {
                producto = data.find(p => p.id_producto === id);
                if (producto) {
                    mostrarDetalle(producto);
                } else {
                    console.error('Producto no encontrado');
                }
            })
            .catch(error => console.error('Error al obtener el producto:', error));
    } else {
        mostrarDetalle(producto);
    }
}

// Función para mostrar el detalle de un producto
function mostrarDetalle(producto) {
    document.getElementById("productos").style.display = "none";
    document.getElementById("detalle").style.display = "block";

    document.getElementById("detalleTitulo").textContent = producto.nombre;
    document.getElementById("detalleImagen").src = producto.imagen;
    document.getElementById("detalleDescripcion").textContent = producto.descripcion;
    document.getElementById("detallePrecio").textContent = `${producto.precio}€`;

    // Configuramos el botón para añadir el producto al carrito
    const botonCarrito = document.querySelector("#detalle button");
    botonCarrito.setAttribute("onclick", `añadirAlCarrito(${producto.id_producto})`);
}

// Función para volver a la vista inicial
function volverInicio() {
    document.getElementById("categorias").style.display = "block";
    document.getElementById("productos").style.display = "none";
    document.getElementById("detalle").style.display = "none";
}

// Función para añadir productos al carrito y guardarlos en localStorage
function añadirAlCarrito(id) {
    let carrito = JSON.parse(localStorage.getItem('carrito')) || [];
    let index = carrito.findIndex(item => item.id === id);

    if (index !== -1) {
        carrito[index].cantidad += 1;
    } else {
        carrito.push({ id: id, cantidad: 1 });
    }

    localStorage.setItem('carrito', JSON.stringify(carrito));
    alert('Producto añadido al carrito');
}

// Cargar productos cuando la página se haya cargado
document.addEventListener("DOMContentLoaded", cargarProductos);
=======
// Array 
let productos = [];
let carrito = JSON.parse(localStorage.getItem('carrito')) || [];

// Carga los productos
function cargarProductos(categoriaSeleccionada = "") {
  fetch('producto.json')  // Obtiene los productos desde el json de productos
    .then(res => res.json())  // transforma a JSON
    .then(json => {
      productos = json;
      let productosFiltrados;
      if (categoriaSeleccionada) {  // Filtra por categoria 
        const mapeoCategorias = {
          'pantalones': 1,
          'sudaderas': 2,
          'camisetas': 3,
          'camisas': 4,
          'chaquetas': 5
        };
        const idCategoria = mapeoCategorias[categoriaSeleccionada.toLowerCase()];
        productosFiltrados = productos.filter(producto => producto.Id_categoria === idCategoria);
      } else {
        productosFiltrados = productos;  // enseña todos los productos si no hay filtro
      }
      mostrarProductos(productosFiltrados);  // muestra los productos filtrados
    })
    .catch(error => console.error('Error al cargar los productos:', error));  // muestra error
}

// Muestra los productos en la interfaz
function mostrarProductos(productosFiltrados) {
  const container = document.getElementById('productoContainer');
  container.innerHTML = '';  // Limpia el contenedor
  productosFiltrados.forEach(producto => {
    const div = document.createElement('div');
    div.innerHTML = `
      <div class="producto">
        <img class="img-producto" src="${producto.imagen}" alt="${producto.nombre}" style="height: 6.5rem; margin-top:20px;" onclick="verDetalle(${producto.id_producto})">
        <h2 style="height:55px; overflow:hidden;">${producto.nombre}</h2>
        <p><b>${producto.precio}€</b></p>
        <button onclick="añadirAlCarrito(${producto.id_producto})">Añadir al carrito</button>
      </div>
      <br>
    `;
    container.appendChild(div);  
  });
}

// filtra los productos por la categoria
function filtrarPorCategoria() {
  const categoriaSeleccionada = document.getElementById('categoriaFiltro').value;
  cargarProductos(categoriaSeleccionada); 
}

// muestra los productos de una categoria 
function mostrarCategoria(categoria) {
  document.getElementById('categorias').style.display = 'none';
  document.getElementById('productos').style.display = 'block';
  document.getElementById('categoriaTitulo').textContent = categoria;
  cargarProductos(categoria); 
}

// Vuelve 
function volverInicio() {
  document.getElementById('categorias').style.display = 'block';
  document.getElementById('productos').style.display = 'none';
  document.getElementById('detalle').style.display = 'none';
}

// Añade un producto al carrito y lo guarda en localStorage
function añadirAlCarrito(id) {
  let carrito = JSON.parse(localStorage.getItem('carrito')) || [];
  let index = carrito.findIndex(item => item.id === id);
  if (index !== -1) {  // Si el producto ya esta en el carrito incrementa su cantidad
    carrito[index].cantidad += 1;
  } else {
    carrito.push({ id: id, cantidad: 1 });  //agrega el producto con cantidad 1
  }
  localStorage.setItem('carrito', JSON.stringify(carrito));  // Guarda el carrito actualizado
  alert('Producto añadido al carrito');
}

// muestra los detalles del producto
function verDetalle(id) {
  const producto = productos.find(p => p.id_producto === id);
  if (producto) {
    localStorage.setItem('productoSeleccionado', JSON.stringify(producto));
    window.location.href = 'detalle.html';
  } else {
    console.error('Producto no encontrado');  
  }
}
>>>>>>> master
