
package proyecto;

//La excepción ClasificacionException es una excepción personalizada que hemos
//creado para representar un error específico al obtener la clasificación desde 
//la base de datos. Cuando ocurre ese error, lanzo esta excepción con un mensaje
//de error descriptivo. Luego, en el código que utiliza el método obtenerClasificacion(),
//capturamos esa excepción y mostramos un mensaje de error personalizado. 
//De esta manera, podemos comunicar de manera adecuada los errores que ocurran en ese 
//proceso específico.

public class ClasificacionException extends Exception {

    public ClasificacionException(String mensaje) {
        super(mensaje);
    }
}
