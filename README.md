# TALLER DE VERIFICACIÓN DE CONOCIMIENTOS TÉCNICOS

## Iniciando 

1. Descargue el siguiente repositorio `https://github.com/AndresOnate/PARCIALT1-AREP.git`
2. Ingrese al directorio del proyecto
3. Compile el proyecto `mvn compile`
4. Abra dos líneas de comandos para los siguientes pasos:
5. Ejecute el servidor API de la calculadora con el siguiente comando `java -cp target/classes org.edu.eci.arep.HttpCalculatorServer`
6. Ejecute el servidor fachada con el siguiente comando `java -cp target/classes org.edu.eci.arep.HttpServer`
  
## Probando la aplicación

Ingrese a la siguiente dirección: `http://localhost:35000/cliente`

![image](https://github.com/AndresOnate/PARCIALT1-AREP/assets/63562181/daa4038d-93b1-4ae2-a121-c9c497dd7c58)

Ingrese en el espacio el comando que esea ejecutar, evite el uso de espacio entre argumentos, es decir, unaryInvoke(java.lang.Math,abs,int,3), como puede apreciar no hay espacio entre las comas

## Ejemplo de ejecución

`Class(java.lang.Math)`

![image](https://github.com/AndresOnate/PARCIALT1-AREP/assets/63562181/32e61824-7808-4f02-860e-984b7d665c33)


`invoke(java.lang.System,getenv)`

![image](https://github.com/AndresOnate/PARCIALT1-AREP/assets/63562181/dcd294d1-fff0-4b86-8c45-c280d589d394)


`unaryInvoke(java.lang.Math,abs,int,-3)`

![image](https://github.com/AndresOnate/PARCIALT1-AREP/assets/63562181/aa1f903a-15dc-417b-a9c3-7735e3537ed8)


`unaryInvoke(java.lang.Integer,valueOf,String,5)`

![image](https://github.com/AndresOnate/PARCIALT1-AREP/assets/63562181/3d70963b-e63c-4236-b9b0-91549d8626cf)
