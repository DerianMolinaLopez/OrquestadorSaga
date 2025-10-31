<div style="display: flex; justify-conten: center; gap: 20px;">
  <img src="https://icon.icepanel.io/Technology/svg/Spring.svg" width="150" height="150" alt="Spring Boot Logo">
  <img src="https://icon.icepanel.io/Technology/svg/Java.svg" width="150" height="150" alt="Java Logo">
  <h1>Orquestador Saga</h1>
</div>

## Objetivo
Implementar un patron de diseño muy similar a pipeline, solo que la diferencia
es que hay una funcion de compensacion que hace la accion inversa que se crea
en el metodo de ejecucion de cada uno de los metodos que implementen StepSaga.

De esta forma se soluciona un problema para una arquitectura basada en eventos
y es la emision de eventos pero tambein el rollback de cada uno de los microservicios.
El orquestador envia informacion a x numero de servicios, y esos servicios reciben 
un fragmento del json para hacer su determinada operacion, sin embargo como los
servicios estan separados y son independientes, si pasa un probelma en un servio
los demas no lo sabran y habran hecho un grabado parcial de la informacion, habra
un componente que no pudo ejecutarse po x o y razon.

Es ahi donde entra el patron saga para orquestar los microservicios, hay un topico
de errores y confirmaciones que el mismo orquestador escucha, normalmente llegan mensajes
en confirmacions si todo va bien, pero enc aso de un rollback, el orquestador va a enviar 
una compesnacion a cada uno de los microservicios para que hagan la operacion inversa.

La informacion relevante es el numero de la oepracion (correlationId) que es usado
en cada uno de los microservicios para grabar la informacion relacionada con una operacion
historica, algunos servicios no borran informacion en la compensacion si no que marcan 
la informacion grabada como cancelada y las cifras de aquello que grabaron se reestablecen
como si no pasarea nada, pero en hisotircos se queda la informacion si o si.

## Planteamiento del problema
El problema es muy simple, dado a una tienda en presencia en todo el pais, esta tiene determinadas
bodegas de donde sale la mercacia, los clientes con sus formas de pago ralizan comrpas masivas a un
mismo sistema para ordenar sus productos, el sistema debe de poder capturar las ordenes y reducir las cifras 
del inventario y marcar la inforamcion de la operacion, hay una seccion de los pagos que es para guardar
la informacion del pago.

El objetivo principal del programa es una vez confirmada toda la comrpa, hay que llenar la informacion de las 
bodegas para saber que se va a descontar del inventario y la informacion que se quede grabada para
proyecciones de comportamieto, para eso es necesario una arquitectura de micorservicios que pueda
manejar cada una de las sub operaciones continuando las compras de determinados clientes.

## Requisitos
1. Docker
2. Un contenedor de kafka
3. Java 20 en adelante
4. maven
## Levantamiento del proyecto 


