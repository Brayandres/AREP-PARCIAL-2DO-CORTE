# PARCIAL 2DO TERCIO - EJERCICIOS DE DISEÑO

Link del Video: [Video AREP](https://pruebacorreoescuelaingeduco-my.sharepoint.com/:v:/g/personal/brayan_macias_mail_escuelaing_edu_co/EVm8XwKZZmBClzvhvxOGt30Bha7ms2z2jNekfM26MSQhqQ)

## REQUERIMIENTOS
Diseñe un prototipo de calculadora de microservicios que tenga un servicios de matemáticas con al menos dos funciones implementadas y desplegadas en al menos dos instancias virtuales de EC2. Además debe implementar un service proxy que recibe las solicitudes de servicios y se las delega a las dos instancias usando un algoritmo de round-robin. Asegúrese que se pueden configurar las direcciones y puertos de las instancias en el porxy usando variables de entorno del sistema operativo. Cada estudiante debe seleccionar para desarrollar dos funciones matemáticas de acuerdo a los dos últimos dígitos de su cédula como se especifica en la lista (Si sus dos últimos dígitos de su cédula son el mismo use el siguiente dígito que sea diferente). Todas las funciones reciben un solo parámetro de tipo "Double" y retornan una parámetro de tipo "Double".

Salida. El formato de la salida y la respuesta debe ser un JSON con el siguiente formato
```JSON
{
    "operation": "cos",
    "input":  3.141592,
    "output":  -0.999999
}
```
## Funcionamiento
La siguiente construcción tiene como finalidad poder brindar un servicio simple de operaciones matemáticas, en donde se manejan los resultados a través de archivos de tipo JSON.
Se cuenta con 3 instancias de EC2, la principal el la llamada "proxy".
Al acceder al proxy, tenemos 2 servicios:
- Resultados a través de una página HTML
  
  ![Page](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Working/Pagex.jpg)

  ![PageSqrt](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Working/PageSqrtx.jpg)  

- Y los resultados de la operación a través de un archivo JSON.
  
  ![JsonSqrt](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Working/JsonSqrtx.jpg)

  ![JsonExp](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Working/JsonExpx.jpg)

Finalmente, también podemos manejar tales operaciones a través de los servicios de "calculator1" y "calculator2", que corresponden a las 2 instancias restantes de  EC2.

## Cómo correr en EC2
Lo primero es crear 3 instancias de EC2 generando llaves privadas propias,

![KeyGeneration](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/KeyGenerationx.jpg)

y esperamos a que se inicien correctamente.
Una ves tengamos listas nuestras 3 instancias de EC2, procederemos a nombrarlas con el servicio que van a tener asociado.

![Instances](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Instancesx.jpg)

Ahora, procedemos a instalar Docker en las 3 máquinas.
para ello, accedemos a cada una de las máquinas a través de SSH usando las claves privadas que generamos en la creación de las instancias.

![Connect](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Connectx.jpg)

![SSHconnection](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/SSHConnectionx.jpg)

para instalar Docker, utilizamos el siguiente comando:

```sudo yum install docker```

Una vez que docker se ha instalado, procedemos a iniciar el Daemon de Docker

```sudo systemctl start docker```

Para poder usar nuestros programas previamente creados, descargaremos y usremos las imágenes que los contienen desde un repositorio público de DockerHub.

![Dockerhub](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Docker/Captura%20Docker%20Hubx.jpg)

Así que levantamos cada uno de los servicios con la imagen correspondiente en cada instancia.

![Run](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Docker/Runx.jpg)

![RunCalculator1](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Docker/RunCalc1x.jpg)

![RunCalculator2](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/Docker/RunCalc2x.jpg)

Ahora va a ser necesario abrir los puertos correspondientes para poder acceder a los servicios en las instancias a través de sus direcciones públicas.
Así que en el apartado de "Seguridad" de cada instancia,

![SecurityGroup](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/SecurityGroupx.jpg)

seleccionamos el grupo de seguridad de esta y nos situamos en las reglas de entrada.
Hacemos clic en editar reglas

![EditRules](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/EditRulesx.jpg)

Agregamos una nueva regla con el puerto 4567 para TCP y libre para todos. Luego damos clic en "Guardar Reglas".

![NewRule](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/NewRulex.jpg)

Para que el agloritmo de RoundRobin dentro del proxy pueda manejar correctamente las direcciones de las otras 2 instancias, será necesario crear 2 variables de entorno con las direcciones de las instancias.
Entramos de nuevo a la máquina del proxy:

creammos el siguiente archivo con privilegios de root user,
```bash
  sudo su
  cat > /etc/profile.d/awsenv.sh

```
e introducimos el siguiente contenido en el archivo, que son las variables de entorno

![EnvVars](https://github.com/Brayandres/AREP-PARCIAL-2DO-CORTE/blob/main/Evidence/EnvVarsx.jpg)

Leugo reiniciamos la máquina desde el panel de AWS.
Finalmente tendremos todo nuestro sistema funcionado correctamente.

### Nota
Dentro del repositorio podremos encontrar los 2 programas: proxy y calculator, cada uno con su respectivo archivo Dockerfile.


## Construido en

* [Maven](https://maven.apache.org/) - Dependency Management

## Despliegue en AWS
(Link de referencia, probablemente no funcional a la hora de probarlo)

[![Deploy](https://pbs.twimg.com/profile_images/1377340526890872832/Qvi0U8pF_200x200.jpg)](http://ec2-54-211-18-5.compute-1.amazonaws.com:4567)

## Control de versiones 

[Github](https://github.com/) para el versionamiento.

## Authors

[Brayan Macías](https://github.com/brayandres) 

_Fecha : 30 de marzo del 2022_ 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE)