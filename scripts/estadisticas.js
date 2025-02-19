document.addEventListener("DOMContentLoaded", () => {
    const verGraficoLink = document.getElementById("verGrafico");
    const graficoImagen = document.getElementById("graficoImagen");
    if (verGraficoLink && graficoImagen) {
      verGraficoLink.addEventListener("click", function(e) {
        e.preventDefault();
        graficoImagen.style.display = graficoImagen.style.display === "none" ? "block" : "none";
      });
    }
    cargarVentasAnuales();
    cargarProductosStockBajo();
    cargarProductosRentables();
    const verListado = document.getElementById("verListado");
    if (verListado) {
      verListado.addEventListener("click", (event) => {
        event.preventDefault();
        abrirPaginaClientesTop();
      });
    }
    fetch("producto.json")
      .then(res => res.json())
      .then(products => {
        mostrarProductosMasVendidos(products);
        mostrarMejoresCalificados(products);
        mostrarMasCarosYBaratos(products);
      })
      .catch(error => console.error("Error al cargar los productos:", error));
  });
  
  function cargarVentasAnuales() {
    //cargamos el json
    fetch("ganancias_por_mes.json")
      .then(response => response.json())
      .then(ventasAnuales => {
        console.log("Ventas anuales cargadas:", ventasAnuales);
        const ventasSection = document.getElementById("ventasSection");
        if (!ventasSection) return;
        const totalGanancias = ventasAnuales.reduce((sum, mes) => sum + mes.ganancias, 0);
        const totalGananciasElement = document.createElement("p");
        totalGananciasElement.textContent = "Total ganancias del año: " + totalGanancias;
        ventasSection.appendChild(totalGananciasElement);
        const listaMeses = document.createElement("ul");
        ventasAnuales.forEach(mes => {
          const li = document.createElement("li");
          li.textContent = "Mes " + mes.mes + ": " + mes.ganancias;
          listaMeses.appendChild(li);
        });
        ventasSection.appendChild(listaMeses);
      })
      .catch(error => console.error("Error al cargar las ventas anuales:", error));
  }
  
  async function cargarProductosStockBajo() {
    try {
      // Selecciona la segunda sección con la clase "seccion"
      const contenedorStock = document.querySelectorAll('.seccion')[1];
      if (!contenedorStock) {
        console.error('No se encontró el contenedor de stock.');
        return;
      }
  
      // Realiza la solicitud para obtener el archivo JSON
      const response = await fetch('productos_bajo_stock.json');
      if (!response.ok) {
        throw new Error(`Error al obtener los datos: ${response.statusText}`);
      }
  
      // Convierte la respuesta a un objeto JSON
      const productosStockBajo = await response.json();
  
      // Verifica que el JSON sea un arreglo
      if (!Array.isArray(productosStockBajo)) {
        throw new Error('El formato de los datos no es el esperado.');
      }
  
      // Crea una lista para mostrar los productos
      const lista = document.createElement('ul');
  
      // Itera sobre los productos y crea un elemento de lista para cada uno
      productosStockBajo.forEach(producto => {
        const nombreProducto = producto.Productos_BajoStock;
        if (nombreProducto) {
          const item = document.createElement('li');
          item.textContent = nombreProducto;
          lista.appendChild(item);
        }
      });
  
      // Limpia el contenedor y agrega la nueva lista
      contenedorStock.innerHTML = '';
      contenedorStock.appendChild(lista);
    } catch (error) {
      console.error('Error al cargar los productos:', error);
    }
  }
  
  let ventanaClientesTop = null;

  function abrirPaginaClientesTop() {
    // Verificar si la ventana ya está abierta y enfocarla
    if (ventanaClientesTop && !ventanaClientesTop.closed) {
      ventanaClientesTop.focus();
      return;
    }
  
    //Abre  una nueva ventana
    ventanaClientesTop = window.open("", "_blank", "width=600,height=400");
  
    // Verificar si la ventana se abrió correctamente
    if (ventanaClientesTop) {
      // Escribir contenido inicial en la nueva ventana
      ventanaClientesTop.document.write(`
        <html>
        <head>
          <title>Clientes Top</title>
          <style>
            body { font-family: Arial, sans-serif; text-align: center; padding: 20px; }
            h2 { color: #333; }
            ul { list-style: none; padding: 0; }
            li { font-size: 18px; padding: 10px; border-bottom: 1px solid #ccc; }
          </style>
        </head>
        <body>
          <h2>Clientes con más pedidos</h2>
          <p>Cargando...</p>
        </body>
        </html>
      `);
      ventanaClientesTop.document.close();
  
      // Realizar la solicitud para obtener los datos del JSON
      fetch("clientes_con_mas_pedidos.json")
        .then(response => response.json())
        .then(clientes => {
          // Generar la lista de clientes
          const listaClientes = clientes.map(cliente => `<li>${cliente.nombre} - ${cliente.total_pedidos} pedidos</li>`).join("");
          // Actualizar el contenido de la ventana con la lista
          ventanaClientesTop.document.body.innerHTML = `
            <h2>Clientes con más pedidos</h2>
            <ul>${listaClientes}</ul>
          `;
        })
        .catch(error => {
          // Manejar errores en la carga del JSON
          ventanaClientesTop.document.body.innerHTML = `<h2>Clientes con más pedidos</h2><p>Error al cargar los datos.</p>`;
          console.error("Error al cargar los clientes:", error);
        });
    } else {
      // Si la ventana fue bloqueada, mostrar un mensaje en la ventana principal
      const parent = document.getElementById("verListado").parentNode;
      let contenedor = document.createElement("div");
      contenedor.innerHTML = "<h2>Clientes con más pedidos</h2><p>Error: Popup bloqueada. Por favor, permita ventanas emergentes para este sitio.</p>";
      parent.appendChild(contenedor);
    }
  }
  
  // Asignar la función al evento click del enlace
  document.getElementById("verListado").addEventListener("click", function(event) {
    event.preventDefault();
    abrirPaginaClientesTop();
  });
  
  function cargarProductosRentables() {
    const contenedorRentables = document.querySelectorAll(".seccion")[4];
    if (!contenedorRentables) return;
    const productosRentables = [
      { nombre: "Chaqueta Premium", imagen: "imgs/ChaquetNike.png" },
      { nombre: "Zapatillas Running", imagen: "imgs/Zapatillas.png" }
    ];
    productosRentables.forEach(producto => {
      let div = document.createElement("div");
      let imagen = producto.imagen || "";
      let nombre = producto.nombre || "";
      div.innerHTML = '<img src="' + imagen + '" alt="' + nombre + '"><p>' + nombre + "</p>";
      contenedorRentables.appendChild(div);
    });
  }
  
  
  function mostrarMasCarosYBaratos(products) {
    const containerCaros = document.getElementById("mas-caros");
    const containerBaratos = document.getElementById("mas-baratos");
  
    // Filtrar productos disponibles y no descontinuados
    const productosDisponibles = products.filter(product => product.Stock > 0 && product.descontinuado === "NO");
  
    // Ordenar productos por precio de mayor a menor
    const masCaros = productosDisponibles.slice().sort((a, b) => b.precio - a.precio).slice(0, 5);
  
    // Ordenar productos por precio de menor a mayor
    const masBaratos = productosDisponibles.slice().sort((a, b) => a.precio - b.precio).slice(0, 5);
  
    // Función para crear y agregar elementos al DOM
    function agregarProductosAlDOM(productos, container) {
      productos.forEach(product => {
        const { nombre = "Sin título", descripcion = "", imagen = "", precio = "N/A" } = product;
        const div = document.createElement("div");
        div.className = "producto";
        div.innerHTML = `
          <h2>${nombre}</h2>
          <img src="${imagen}" alt="${nombre}">
          <p>${descripcion}</p>
          <p>Precio: ${precio}€</p>
        `;
        container.appendChild(div);
      });
    }
  
    // Agregar productos más caros y más baratos al DOM
    agregarProductosAlDOM(masCaros, containerCaros);
    agregarProductosAlDOM(masBaratos, containerBaratos);
  }
  
  // Función para mostrar los productos menos comprados
function mostrarProductosMenosComprados(productos) {
    // Obtener el contenedor donde se mostrarán los productos
    const contenedorMenosComprados = document.getElementById("productos-menos-comprados");
  
    // Verificar si el contenedor existe
    if (!contenedorMenosComprados) {
      console.error("No se encontró el contenedor con el ID 'productos-menos-comprados'.");
      return;
    }
  
    // Limpiar el contenido previo del contenedor
    contenedorMenosComprados.innerHTML = "";
  
    // JSON de productos menos comprados
const productosMenosComprados = [
    { "PRODUCTOS_MENOS_COMPRADOS": "Camisa a cuadros", "PRODUCTO_ID": 15 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Camisa hawaiana", "PRODUCTO_ID": 17 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Chaqueta de cuero", "PRODUCTO_ID": 18 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Chaqueta de mezclilla", "PRODUCTO_ID": 19 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Chaqueta acolchada", "PRODUCTO_ID": 20 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Camiseta versace x Offwhite", "PRODUCTO_ID": 25 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Pantalon Offwhite", "PRODUCTO_ID": 27 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Camiseta Armani exchange", "PRODUCTO_ID": 28 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Pantalon Adidas", "PRODUCTO_ID": 29 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Camiseta jordan", "PRODUCTO_ID": 30 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Producto1", "PRODUCTO_ID": 148 },
    { "PRODUCTOS_MENOS_COMPRADOS": "Producto1", "PRODUCTO_ID": 168 }
  ];
  
  // Función para mostrar los productos en la lista
  function mostrarProductosMenosComprados() {
    // Obtener el contenedor donde se mostrarán los productos
    const lista = document.getElementById('productos-lista');
  
    // Limpiar el contenido previo del contenedor
    lista.innerHTML = '';
  
    // Recorrer el array de productos y agregar cada uno a la lista
    productosMenosComprados.forEach(producto => {
      const li = document.createElement('li');
      li.textContent = `${producto.PRODUCTOS_MENOS_COMPRADOS} (ID: ${producto.PRODUCTO_ID})`;
      lista.appendChild(li);
    });
  }
  
  // Llamar a la función para mostrar los productos
  mostrarProductosMenosComprados();
}  
  
  