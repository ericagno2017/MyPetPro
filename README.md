# MyPetPro
Pet Vet Pro

Ejemplo de API REST, utilizando Spring, JPA, hibernate
Se manejan 3 entidades
Clinic
PetOwner
Pet
Cada uno cuenta con atributos basicos. Como nombre
direccion, id, etc.

Una Clinic puede tener n PetOwner.
Un PetOwner puete tener n Pet.
Se implementan los endpoints para guardar cada 
de las entidades, hacer query por nombre, por id
y busqueda paginada.
No se implementa autenticacion con Spring Security
para simplificar el ejemplo, sino habria que generar
el modelo de usuarios, roles y privilegios. Para
autenticacion. Despues generar los endpoints correspondientes
para brindar basicamente un token de acceso y poder acceder.
Tampoco se usan DTO y transformadores de DTO, para la
exposicion de los datos. Tambien para simplificar modelo.
Para insertar una entidad hijo, se requiere brindar 
minimamente el id de la entidad padre.
Para que no se puedan insertar huerfanos.

Los test implementados son de Integracion directamente,
dado que permiten probar todo el ciclo de vida para
una determinada accion.

Esta implementado con un DB H2 en momoria, pero ya
estan definidos los parametros para POSTGRESQL.

La ejecucion directa pemite el acceso a los endpoints
definidos en el swagger:
http://localhost:8080/pet-pro/swagger-ui.html

en 

http://localhost:8080/pet-pro/h2/login.jsp

esta la consola de H2 para poder probar el almacenamiento
de los datos.

URL a configurar jdbc:h2:mem:test

user name sa


no tiene password.
Cuando arranca carga un set de datos basicos para 
que se puedan consultar.

Son 2 Clinicas, 2 usuarios por clinica y 5 mascotas por usuario.