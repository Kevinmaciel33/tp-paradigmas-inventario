# unlam-chemestry-crafter

Es un juego de quimica, en donde los elemenos nos dejan crear nuevas cosas  


## Diseño

Desarrolado con java 17 y maven

- `Query` es un enum que tiene las querys a correr
- `Crafter` es una base sobre la cual crear catalizadores
- `Classification` es el enum para especificar donde se usan los catalizadores
- `Worker` solo llama las querys y los crafters 
- `Loader` lee archivos. Puede que sean en un hilo aparte o en un scheduler/cron
    - Puede que se pueda llevar logica  a la interfaz y dejar en cada loader la creacion del objeto solamente
- `main` lee los files y crea un worker


## TODO

- No probe que leyera el archivo
  - Me falta ver que cree bien los objetos
- Tests
- Menu principal
  - Poder leer inputs, sanitizarlos, y llamar al worker
- Todos los nombres estan sujetos a cambios. Pueden dejar ideas
- Todas las querys
- Todos los crafters
