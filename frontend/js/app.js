const formatear = (num) => Number(num).toFixed(2) + " €";

async function cargarDatos() {
    const id = document.getElementById("alumnoId").value;
    const mensaje = document.getElementById("mensaje");

    mensaje.innerText = "";

    if (!id || id <= 0) {
        mensaje.innerText = "Introduce un ID de alumno válido.";
        return;
    }

	document.getElementById("barraPagado").style.width = "0%";
	document.getElementById("barraPendiente").style.width = "0%";
	
    try {
        document.getElementById("total").innerText = "Cargando...";

        const cursosRes = await fetch(`http://localhost:8080/api/matriculas/alumno/${id}/cursos`);
        const cursos = await cursosRes.json();

        const cursosList = document.getElementById("cursos");
        cursosList.innerHTML = "";

        if (cursos.length === 0) {
            cursosList.innerHTML = "<li>No hay cursos</li>";
        } else {
            cursos.forEach(c => {
                cursosList.innerHTML += `<li>${c.nombre} (${c.nivel})</li>`;
            });
        }

        const pagosRes = await fetch(`http://localhost:8080/api/pagos/alumno/${id}`);
        const pagos = await pagosRes.json();
		
		if (pagos.length > 0) {
		    document.getElementById("nombreAlumno").innerText = "Alumno: " + pagos[0].nombreAlumno;
		} else {
		    document.getElementById("nombreAlumno").innerText = "";
		}

        const pagosList = document.getElementById("pagos");
        pagosList.innerHTML = "";

        if (pagos.length === 0) {
            pagosList.innerHTML = "<li>No hay pagos</li>";
        } else {
            pagos.forEach(p => {
                pagosList.innerHTML += `<li>${p.fecha} - ${formatear(p.importe)} (${p.metodo})</li>`;
            });
        }

        const pendienteRes = await fetch(`http://localhost:8080/api/pagos/alumno/${id}/pendiente`);
        const pendiente = await pendienteRes.json();

        const pendienteElem = document.getElementById("pendiente");
        pendienteElem.innerText = formatear(pendiente);
        pendienteElem.style.color = pendiente > 0 ? "#dc3545" : "#28a745";

        const totalRes = await fetch(`http://localhost:8080/api/pagos/alumno/${id}/total`);
        const total = await totalRes.json();

        document.getElementById("total").innerText = formatear(total);
		
		const sumaEconomica = Number(total) + Number(pendiente);

		const porcentajePagado = sumaEconomica > 0 
		    ? (Number(total) / sumaEconomica) * 100 
		    : 0;

		const porcentajePendiente = sumaEconomica > 0 
		    ? (Number(pendiente) / sumaEconomica) * 100 
		    : 0;

		document.getElementById("barraPagado").style.width = porcentajePagado + "%";
		document.getElementById("barraPendiente").style.width = porcentajePendiente + "%";
		
    } catch (error) {
        document.getElementById("total").innerText = "Error al cargar datos";
        console.error(error);
    }
	
}

async function crearPago() {
    const alumnoId = document.getElementById("alumnoId").value;
	const cursoId = document.getElementById("cursoId").value;
    const importe = document.getElementById("importe").value;
    const metodo = document.getElementById("metodo").value;
    const nota = document.getElementById("nota").value;

    if (!alumnoId || !importe) {
        alert("Faltan datos");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/pagos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
			body: JSON.stringify({
			    alumnoId: Number(alumnoId),
			    cursoId: Number(cursoId),
			    fecha: new Date().toISOString().split("T")[0],
			    importe: Number(importe),
			    metodo: metodo,
			    nota: nota
			})
        });
		if (!response.ok) {
		    throw new Error("No se pudo registrar el pago");
		}
		
		// limpiar inputs
		document.getElementById("importe").value = "";
		document.getElementById("nota").value = "";

		// mostrar mensaje después de recargar
		const mensaje = document.getElementById("mensaje");
		mensaje.innerText = "Pago registrado correctamente";
		mensaje.style.color = "#28a745";
		window.scrollTo({
		    top: 0,
		    behavior: "smooth"
		});
		
		// REDIRECCIÓN
		setTimeout(() => {
		    window.location.href = "alumno.html";
		}, 1500);
		
    } catch (error) {
		const mensaje = document.getElementById("mensaje");
		mensaje.innerText = "Error al registrar el pago";
		mensaje.style.color = "#dc3545";
    }
}
async function crearAlumno() {
    const nombre = document.getElementById("nombre").value;
    const apellidos = document.getElementById("apellidos").value;
    const email = document.getElementById("email").value;
    const telefono = document.getElementById("telefono").value;
    const cursoId = document.getElementById("cursoId").value;
    const mensaje = document.getElementById("mensaje");

    mensaje.innerText = "";

    if (!nombre || !apellidos || !email) {
        mensaje.innerText = "Nombre, apellidos y email son obligatorios.";
        mensaje.style.color = "#dc3545";
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/alumnos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nombre: nombre,
                apellidos: apellidos,
                email: email,
                telefono: telefono,
                activo: true
            })
        });

        if (!response.ok) {
            throw new Error("No se pudo registrar el alumno");
        }

        const alumnoCreado = await response.json();

        if (cursoId) {
            const matriculaResponse = await fetch("http://localhost:8080/api/matriculas", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    alumnoId: alumnoCreado.idAlumno,
                    cursoId: Number(cursoId),
                    fechaMatricula: new Date().toISOString().split("T")[0],
                    estado: "ACTIVA"
                })
            });

            if (!matriculaResponse.ok) {
                throw new Error("Alumno creado, pero no se pudo matricular en el curso");
            }
        }

        mensaje.innerText = cursoId
            ? "Alumno registrado y matriculado correctamente"
            : "Alumno registrado correctamente";
        mensaje.style.color = "#28a745";

        document.getElementById("nombre").value = "";
        document.getElementById("apellidos").value = "";
        document.getElementById("email").value = "";
        document.getElementById("telefono").value = "";
        document.getElementById("cursoId").value = "";

        cargarAlumnos();

        setTimeout(() => {
            mensaje.innerText = "";
        }, 5000);

    } catch (error) {
        mensaje.innerText = "Error al registrar el alumno";
        mensaje.style.color = "#dc3545";
        console.error(error);
    }
}

async function cargarAlumnos() {
    const lista = document.getElementById("listaAlumnos");

    if (!lista) {
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/alumnos");
        const alumnos = await response.json();

        lista.innerHTML = "";

        if (alumnos.length === 0) {
            lista.innerHTML = "<li>No hay alumnos registrados</li>";
        } else {
            alumnos.forEach(a => {
                lista.innerHTML += `<li>${a.idAlumno} - ${a.nombre} ${a.apellidos} (${a.email})</li>`;
            });
        }

    } catch (error) {
        lista.innerHTML = "<li>Error al cargar alumnos</li>";
        console.error(error);
    }
}

async function crearCurso() {
    const nombre = document.getElementById("nombreCurso").value;
    const nivel = document.getElementById("nivel").value;
    const horario = document.getElementById("horario").value;

    const precioMensual = document.getElementById("precioMensual").value;
    const precioTrimestral = document.getElementById("precioTrimestral").value;
    const precioAnual = document.getElementById("precioAnual").value;

    const mensaje = document.getElementById("mensaje");
    mensaje.innerText = "";

    if (!nombre || !nivel) {
        mensaje.innerText = "Nombre y nivel son obligatorios";
        mensaje.style.color = "#dc3545";
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/cursos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                nombre: nombre,
                nivel: nivel,
                horario: horario,
                precioMensual: Number(precioMensual),
                precioTrimestral: Number(precioTrimestral),
                precioAnual: Number(precioAnual),
                activo: true
            })
        });

        if (!response.ok) {
            throw new Error("Error al crear curso");
        }

        mensaje.innerText = "Curso creado correctamente";
        mensaje.style.color = "#28a745";

        document.getElementById("nombreCurso").value = "";
        document.getElementById("nivel").value = "";
        document.getElementById("horario").value = "";
        document.getElementById("precioMensual").value = "";
        document.getElementById("precioTrimestral").value = "";
        document.getElementById("precioAnual").value = "";

        cargarCursos();

        setTimeout(() => {
            mensaje.innerText = "";
        }, 5000);

    } catch (error) {
        mensaje.innerText = "Error al crear curso";
        mensaje.style.color = "#dc3545";
        console.error(error);
    }
}

async function cargarCursos() {
    const lista = document.getElementById("listaCursos");

    if (!lista) return;

    try {
        const response = await fetch("http://localhost:8080/api/cursos");
        const cursos = await response.json();

        lista.innerHTML = "";

        if (cursos.length === 0) {
            lista.innerHTML = "<li>No hay cursos</li>";
        } else {
            cursos.forEach(c => {
                lista.innerHTML += `<li>${c.idCurso} - ${c.nombre} (${c.nivel})</li>`;
            });
        }

    } catch (error) {
        lista.innerHTML = "<li>Error al cargar cursos</li>";
        console.error(error);
    }
}

async function cargarCursosSelect() {
    const select = document.getElementById("cursoId");

    if (!select) return;

    try {
        const response = await fetch("http://localhost:8080/api/cursos");
        const cursos = await response.json();

        select.innerHTML = '<option value="">Seleccionar curso</option>';

        cursos.forEach(c => {
            select.innerHTML += `
                <option value="${c.idCurso}">
                    ${c.nombre} (${c.nivel})
                </option>
            `;
        });

    } catch (error) {
        console.error("Error al cargar cursos en el desplegable", error);
    }
}

window.addEventListener("load", () => {
    cargarAlumnos();
    cargarCursos();
    cargarCursosSelect();
});
