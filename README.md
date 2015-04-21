## Práctica 3 PR4. Programación concurrente en Java.

### Cola en las cajas de un hipermercado

Se desea construir un sistema que simule el funcionamiento de las cajas de un hipermercado 
en el que existe una cola única de clientes. Los clientes se dirigen desde la cola a una 
de las cajas existentes (no hay una cola en cada caja). Además, las cajas dan cuenta a 
la contabilidad de los importes de las ventas.

En este sistema se han identificado las siguientes clases: **Cliente, Cola, Caja y Contabilidad**.

#### Clase cliente

La clase **Cliente** se suministra implementada y se utiliza en la simulación para representar 
los clientes que interactúan en la cola y las cajas. El constructor no requiere parámetros. 
El cliente creado dispone de un *nombre*, accesible con **“dameNombre()”**, el *precio* del carro 
que lleva, que se obtiene mediante **“damePrecioCarro()”** y el método **“toString()”** 
que nos suministra toda esta información en forma de String.

#### Clase Cola

Los objetos de la clase **Cola** se encargan de almacenar los objetos **Cliente** que 
esperan ser atendidos. La *salida* de un cliente de la *cola* se produce por su **principio**. 
Las operaciones que soportan la Cola son **“añadirFinal()”** y **“añadirPrincipio()”** que 
añade un nuevo cliente y **“sacar()”** que devuelve (y extrae) el cliente que esté al 
principio de la cola, para lo cual si la cola está abierta puede *esperar un máximo 
de 10 segundos*, al cabo de los cuales devolvería **null**. La cola se puede cerrar 
llamando al método **"cerrar()"**. Una cola cerrada no acepta nuevos clientes por el final 
(el método “añadirFinal()” no hace nada) y cuando se pretende sacar un cliente, 
y no queda ninguno, “sacar()” **devuelve inmediatamente null**. La cola, además, 
nos devuelve el tamaño máximo que ha alcanzado desde su creación con 
el método **“tamañoMáximo()”**.

#### Clase Caja

Los objetos de la clase **Caja** se crean pasándole al constructor un objeto **Cola** 
y uno **Contabilidad**. La clase Caja se encarga de pedir un Cliente a la Cola, 
atenderlo y *repetir* el proceso. **Si la cola devuelve un cliente null, la caja 
se cierra**. El tiempo de atención es el precio del carrito dividido por 10 en 
segundos. Cada caja tendrá un identificador único autogenerado por la clase 
(use atributos static) que se empleará en el mostrado de su estado. Las cajas 
pueden cerrarse también mediante el **"interrupt()"** de Thread. En este caso, si 
se está procesando un cliente éste se volverá a introducir en la cola por el 
principio. El método **"run()"** de una caja termina al ser cerrada.

**Cuando una caja se cierra, se actualiza el objeto Contabilidad con la suma de 
importes de las compras completadas en esa caja.**

#### Clase Contabilidad

Los objetos de la clase **Contabilidad** disponen de dos métodos: **“añadeSaldo()”** que 
añade una cantidad al saldo, y **“dameSaldo()”**, que devuelve la cantidad acumulada. 
Cuando se crean tienen saldo cero.

#### Clase DuendeAveria
La clase **DuendeAveria**, **se suministra implementada aunque no se muestra el código**. 
Un objeto de esta clase se encarga de averiar cajas aleatoriamente llamando al 
método **"interrupt()"** del hilo de la Caja pasada. La clase dispone de un constructor 
que acepta array de objetos Caja o contenedores de objetos Caja. Las cajas pasadas 
al constructor deben haber sido iniciadas con "start()" previamente.

#### Simulación

El programa principal se ha de encargar de pedir el número de cajas a usar y el 
número de clientes de la simulación. Se crearán y arrancarán los elementos 
que intervienen de forma que los clientes se irán añadiendo a la cola con una 
cadencia aleatoria de entre cero y 5. La cola se cerrará al cabo de 60 segundos. 
Al procesar el último cliente, o si no quedaran cajas abiertas, se detendrá el programa.

Se simulará asimismo el que las cajas pueden, por avería, dejar de funcionar. Para ello 
se suministra una clase DuendeAveria que ejecuta, en un hilo a parte, 
la acción de averiar las cajas aleatoriamente con interrupt(). Para que 
se produzca este proceso de averias es suficicnete es suficiente con crear 
un objeto de la clase DuendeAveria al que se le pase un array o Collection con las Cajas.

#### VISUALIZACIÓN

Para tener un percepción de lo que ocurre se deben mostrar información que incluya 
instante (hora) y estado del objeto implicado en cada uno de los siguientes eventos:

* Se añade un cliente a la cola.

* Se saca un cliente de la cola.

* Se inicia el procesamiento de un cliente en una caja.

* Se finaliza el procesamiento de un cliente en una caja.

* Se añaden datos a la contabilidad.
