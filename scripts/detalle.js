document.addEventListener("DOMContentLoaded", function () {
    // Obtener el producto guardado en localStorage
    const producto = JSON.parse(localStorage.getItem('productoSeleccionado'));

    if (!producto) {
        alert("No se encontró información del producto.");
        window.close();
        return;
    }

    // Mostrar los detalles en la página
    document.getElementById("detalleTitulo").textContent = producto.nombre;
    document.getElementById("detalleImagen").src = producto.imagen;
    document.getElementById("detalleDescripcion").textContent = producto.descripcion;
    document.getElementById("detallePrecio").textContent = `${producto.precio}€`;

    // Configurar botón de añadir al carrito
    document.getElementById("btnCarrito").addEventListener("click", function () {
        añadirAlCarrito(producto.id_producto);
    });
});

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
