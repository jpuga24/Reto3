document.addEventListener("DOMContentLoaded", () => {
  console.log("DOM cargado, iniciando fetch de producto.json");
  fetch('producto.json') // Asumimos que tu archivo JSON está en el servidor
    .then(res => {
      if (!res.ok) {
        throw new Error(`Error en la respuesta: ${res.status}`);
      }
      return res.json();
    })
    .then(json => {
      console.log("Datos obtenidos:", json);
      productosGlobal = json; // Guardamos los productos
      mostrarProductos(productosGlobal); // Mostramos los productos al cargar la página
      console.log("Productos agregados al DOM");
      inicializarCarrusel();
    })
    .catch(error => console.error('Error al cargar los productos:', error));

  configurarBuscador(); // Configuramos el buscador
});

// Función para mostrar los productos en el contenedor
function mostrarProductos(productos) {
  const container = document.getElementById('productoContainer');
  container.innerHTML = ''; // Limpiar el contenedor
  if (productos.length === 0) {
    container.innerHTML = '<p>No se encontraron productos.</p>';
    return;
  }
  productos.forEach(producto => {
    const div = document.createElement('div');
    div.classList.add('producto');
    div.innerHTML = `
      <img class="img-producto" src="${producto.imagen}" alt="${producto.nombre}" onclick="verProducto(${producto.id_producto})" style="height:150px; margin-top:20px;">
      <h2>${producto.nombre}</h2>
      <p><b>${producto.precio}€</b></p>
    `;
    container.appendChild(div);
  });
}

// Función para inicializar el carrusel de productos
function inicializarCarrusel() {
  const track = document.getElementById('productoContainer');
  const slides = track.children;
  console.log("Cantidad de slides:", slides.length);
  if (slides.length === 0) return;

  const slideWidth = slides[0].getBoundingClientRect().width;
  console.log("Ancho del slide:", slideWidth);

  setInterval(() => {
    currentIndex = (currentIndex + 1) % slides.length;
    track.style.transform = `translateX(-${currentIndex * (slideWidth + 16)}px)`;
    console.log("Transform aplicado:", track.style.transform);
  }, 3000);
}

// Función para configurar el buscador
function configurarBuscador() {
  const buscador = document.getElementById("Buscador");
  if (!buscador) return;

  buscador.addEventListener("click", () => {
    const query = prompt("Ingrese el término de búsqueda:");
    if (query === null) return; // Si se cancela, no se hace nada

    const queryLower = query.trim().toLowerCase();
    // Filtramos los productos por nombre
    const productosFiltrados = productosGlobal.filter(producto =>
      producto.nombre.toLowerCase().includes(queryLower) // Buscamos coincidencias en el nombre
    );
    mostrarProductos(productosFiltrados); // Actualizamos los productos mostrados
  });
}

// Función para ver un producto (puedes ampliar su funcionalidad)
function verProducto(id) {
  console.log("Ver producto:", id);
  // Aquí podrías redirigir a una página de detalles o mostrar un modal
}