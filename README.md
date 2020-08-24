
# LAB2 - JAVA IMMORTALS

### Descripción

En este repositorio se encontrará la solución al laboratiorio 2 de ARSW,
encontraremos un proyecto maven con la solución. A continuación se darán
las instrucciones de instalación y las respuestas a los ejercicios.

### Pre requisitos

Para correr el proyecto debe tener instalados los siguientes programas
en su computador:

- Java SE Development Kit 8
- Java SE Runtime Environment 8
- Maven para poder construir el proyecto.
- Git para clonar el repositorio. 

### Instrucciones de instalación

Desde la terminal ejecutar los siguientes comandos:
```
 git clone https://github.com/AlejandroBohal/LAB2-IMMORTALS
 cd LAB2-IMMORTALS
```
compilar el proyecto con el siguiente comando:
```
 mvn package
```
Para correr la solución del problema productor consumidor:
```
mvn exec:java -Dexec.mainClass="edu.eci.arst.concprg.prodcons.StartProduction"
```
Para correr la solución al problema immortals:
```
mvn exec:java -Dexec.mainClass="edu.eci.arsw.highlandersim.ControlFrame";
```
### Pruebas

Para correr las pruebas usar el siguiente comando.
```
mvn test;
```


### Part I - Before finishing class

1 Check the operation of the program and run it. While this occurs, run jVisualVM and check the CPU consumption of the corresponding process. Why is this consumption? Which is the responsible class? 

El uso de la cpu es en promedio del 25% lo cual es muy alto, esta sucediendo debido a que tanto el consumidor como el productor tienen una espera activa, además no se esta sincronizando el acceso a la cola ni en la lectura ni en la escritura.

Uso de Cpu
![](https://cdn.discordapp.com/attachments/352624122301513730/745730740528087060/Memory_usage.png)
Consumidor
![](https://cdn.discordapp.com/attachments/352624122301513730/745730673863688212/consumerFirst.png)
Productor
![](https://cdn.discordapp.com/attachments/352624122301513730/745730932778205294/unknown.png)

2 Make the necessary adjustments so that the solution uses the CPU more efficiently, taking into account that - for now - production is slow and consumption is fast. Verify with JVisualVM that the CPU consumption is reduced. 

Correción consumidor
![](https://cdn.discordapp.com/attachments/352624122301513730/745737649955930113/unknown.png)

Correción productor
![](https://cdn.discordapp.com/attachments/352624122301513730/745736258268954674/unknown.png)

Consumo de memoria luego de la corrección:

Podemos observar que el consumo de memoria disminuyo significativamente con respecto al inicio.
![](https://cdn.discordapp.com/attachments/352624122301513730/745738245605687356/unknown.png)


3 Make the producer now produce very fast, and the consumer consumes slow. Taking into account that the producer knows a Stock limit (how many elements he should have, at most in the queue), make that limit be respected. Review the API of the collection used as a queue to see how to ensure that this limit is not exceeded. Verify that, by setting a small limit for the 'stock', there is no high CPU consumption or errors.

Consumidor
![](https://cdn.discordapp.com/attachments/352624122301513730/745741350900727929/unknown.png)
Productor
![](https://cdn.discordapp.com/attachments/352624122301513730/745742053891375224/unknown.png)

Probando con un limite pequeño para el stock del productor:

![](https://cdn.discordapp.com/attachments/352624122301513730/745743286358245406/unknown.png)

Resultado Cpu (no se presentaron errores.)

![](https://cdn.discordapp.com/attachments/352624122301513730/745743363432775690/unknown.png)

### Part 2 - Synchronization and Dead-Locks.

For this case, for N players, what should this value be?

En cualquier caso, el valor total de la vida debería ser igual a
N*100, siendo N el numero de jugadores y 100 la vida base que
se asigna al principio a cada uno.

Run the application and verify how the ‘pause and check’ option works. Is the invariant fulfilled?

La invariante no se esta cumpliendo ya que el acceso a la 
variable de la vida no se esta sincronizando o sus operaciones
no son atómicas, esto genera una condición de carrera de tipo
read-modify-write, y se podría solucionar sincronizando el acceso
o utilizando un AtomicInteger.

Check the operation again (click the button many times). Is the invariant fulfilled or not ?.

La invariante se esta cumpliendo una vez implementada la solución
propuesta en la pregunta anterior.

It is necessary to suppress the immortal dead of the simulation as they die. 
Analyzing the simulation operation scheme, could this create a race condition? 

Podría haber condición de carrera de modificación ya que mientras que
los hilos que siguen vivos se sincronizan, aquellos que están
muertos no necesitan sincronización ni operaciones adicionales,
por tal motivo se decidió eliminar aquellos que mueran a medida
suceda.

### Ejemplo con 100 hilos

![](https://cdn.discordapp.com/attachments/352624122301513730/747263600682467408/unknown.png)

