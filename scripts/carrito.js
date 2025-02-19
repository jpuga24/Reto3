document.addEventListener("DOMContentLoaded", () => {
    cargarProductos();
    actualizarCarrito();
});

// Cargar productos desde API
// Cargar productos en el carrito
function cargarProductos() {
    const carrito = JSON.parse(localStorage.getItem('carrito')) || [];
    const container = document.getElementById('productos-lista');
    container.innerHTML = '';  // Limpiar productos anteriores
    let total = 0;

<<<<<<< HEAD
    carrito.forEach(item => {
        // Hacer fetch de cada producto según el ID guardado en el carrito
        fetch(`producto.json${item.id}`)
            .then(response => response.json())
            .then(producto => {
                const div = document.createElement('div');
                div.classList.add('producto');
                div.innerHTML = `
                    <img src="${producto.image}" alt="${producto.title}">
                    <div class="detalles">
                        <strong>${producto.title}</strong><br>
                        <span class="precio">${producto.price}€</span>
                    </div>
                    <div class="cantidad">
                        <button onclick="cambiarCantidad(${item.id}, -1)">-</button>
                        <span>${item.cantidad}</span>
                        <button onclick="cambiarCantidad(${item.id}, 1)">+</button>
                    </div>
                `;
                container.appendChild(div);
                total += producto.price * item.cantidad;
                document.getElementById('total').innerText = `Total: ${total.toFixed(2)}€`;
                actualizarEnvio(total);
            })
            .catch(error => console.error('Error al cargar productos:', error));
    });
=======
    fetch('producto.json')
        .then(response => response.json())
        .then(productos => {
            carrito.forEach(item => {
                const producto = productos.find(p => p.id_producto === item.id);
                if (producto) {
                    const div = document.createElement('div');
                    div.classList.add('producto');
                    div.innerHTML = `
                        <img src="${producto.imagen}" alt="${producto.nombre}">
                        <div class="detalles">
                            <strong>${producto.nombre}</strong><br>
                            <span class="precio">${producto.precio}€</span>
                        </div>
                        <div class="cantidad">
                            <button onclick="cambiarCantidad(${item.id}, -1)">-</button>
                            <span>${item.cantidad}</span>
                            <button onclick="cambiarCantidad(${item.id}, 1)">+</button>
                        </div>
                    `;
                    container.appendChild(div);
                    total += producto.precio * item.cantidad;
                }
            });
            document.getElementById('total').innerText = `Total: ${total.toFixed(2)}€`;
            actualizarEnvio(total);
        })
        .catch(error => console.error('Error al cargar productos:', error));
>>>>>>> master
}


// Cambiar cantidad
function cambiarCantidad(productId, cambio) {
    let carrito = JSON.parse(localStorage.getItem('carrito')) || [];
    let index = carrito.findIndex(item => item.id === productId);

    if (index !== -1) {
        carrito[index].cantidad += cambio;

        if (carrito[index].cantidad <= 0) {
            if (confirm('¿Eliminar este producto del carrito?')) {
                carrito.splice(index, 1);
            } else {
                carrito[index].cantidad = 1;
            }
        }

        localStorage.setItem('carrito', JSON.stringify(carrito));
        cargarProductos();
        actualizarCarrito();
    }
}

// Añadir producto
// Añadir producto al carrito
function añadirAlCarrito(productId) {
    let carrito = JSON.parse(localStorage.getItem('carrito')) || [];
    let index = carrito.findIndex(item => item.id === productId);

    if (index !== -1) {
        // Si el producto ya está en el carrito, aumentar la cantidad
        carrito[index].cantidad += 1;
    } else {
        // Si el producto no está en el carrito, añadirlo con cantidad 1
        carrito.push({ id: productId, cantidad: 1 });
    }

    // Guardar el carrito actualizado en localStorage
    localStorage.setItem('carrito', JSON.stringify(carrito));
    
    // Actualizar la vista del carrito
    actualizarCarrito();

    alert('Producto añadido al carrito');
}


// Actualizar carrito visualmente
function actualizarCarrito() {
    const carrito = JSON.parse(localStorage.getItem('carrito')) || [];
    const carritoLink = document.getElementById('carritoLink');
    
    // Asegurarse de que existe el elemento para actualizar
    if (carritoLink) {
        carritoLink.innerHTML = `<img src="fotos/carrito.png" alt="carrito"> (${carrito.length})`;
    }
}


// Envío gratis
function actualizarEnvio(total) {
    const envioElemento = document.getElementById('envio-gratis');
    envioElemento.textContent = total >= 40 ? "ENVÍO 24H GRATIS APLICADO" : "ENVÍO 24H GRATIS A PARTIR DE 40€";
}

// Vaciar carrito
function vaciarCarrito() {
    localStorage.removeItem('carrito');
    alert('Carrito vaciado');
    cargarProductos();
}

// Finalizar compra
function GraciasCompra() {
    alert('Gracias por su compra');
    localStorage.removeItem('carrito');
    window.location.href = 'index.html';
}
