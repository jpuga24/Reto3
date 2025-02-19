document.addEventListener("DOMContentLoaded", () => {
  console.log("DOM cargado, iniciando fetch de producto.json");
  fetch('producto.json')
    .then(res => {
        if (!res.ok) {
            throw new Error(`Error en la respuesta: ${res.status}`);
        }
        return res.json();
    })
    .then(json => {
      console.log("Datos obtenidos:", json);
      const container = document.getElementById('productoContainer');
      container.innerHTML = '';
      json.forEach(producto => {
        const div = document.createElement('div');
        div.classList.add('producto');
        div.innerHTML = `
          <img class="img-producto" src="${producto.imagen}" alt="${producto.nombre}" onclick="verProducto(${producto.id_producto})" style="height:150px; margin-top:20px;">
          <h2>${producto.nombre}</h2>
          <p><b>${producto.precio}€</b></p>
        `;
        container.appendChild(div);
      });
      console.log("Productos agregados al DOM");
      inicializarCarrusel();
    })
    .catch(error => console.error('Error al cargar los productos:', error));

  configurarBuscador();
});

let currentIndex = 0;

function inicializarCarrusel(){
  const track = document.getElementById('productoContainer');
  const slides = track.children;
  console.log("Cantidad de slides:", slides.length);
  if (slides.length === 0) return;

  // Calcula el ancho de la primera tarjeta
  const slideWidth = slides[0].getBoundingClientRect().width;
  console.log("Ancho del slide:", slideWidth);

  setInterval(() => {
    currentIndex = (currentIndex + 1) % slides.length;
    track.style.transform = `translateX(-${currentIndex * (slideWidth + 16)}px)`;
    console.log("Transform aplicado:", track.style.transform);
  }, 3000);
}

function configurarBuscador(){
  const buscador = document.getElementById("Buscador");
  if (!buscador) return;
  buscador.addEventListener("click", () => {
    console.log("Funcionalidad de búsqueda pendiente de implementación");
    alert("Función de búsqueda aún no disponible");
  });
}

function verProducto(id) {
  console.log("Ver producto:", id);
}
