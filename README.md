IMMORTALS

# LAB2 - JAVA IMMORTALS


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