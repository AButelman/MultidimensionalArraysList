import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListArray {
    
	private ArrayList<Object> lista;
	
	public ListArray() {
		lista = new ArrayList<Object>();
	}
	
	/**
	 * Muestra los detalles de los arrays en el orden en que se encuentran guardados en la lista.
	 */
	public void mostrar() {
		int i = 1;
		
		for (Object obj : lista) {
			
			System.out.printf("%d: Array de tipo %s, con %d dimensiones de tamaño %d. Celdas totales: %d.\n", i, obtenerTipo(obj), 
					obtenerDimensiones(obj), Array.getLength(obj), obtenerCeldas(obj));
			i++;
		}
	}

	/**
	 * Agrega un array de cualquier cantidad de dimensiones a su ubicación en la lista, la cual dependerá de la cantidad de 
	 * celdas totales que éste contenga y de la cantidad de celdas del resto de arrays en la lista, ordenados de menor a mayor.
	 * @param obj el array a agregar.
	 * @throws IllegalArgumentException si el objeto no es del tipo array.
	 */
	public void agregarArray(Object obj) throws IllegalArgumentException {
		int posicion = 0;
		
		if (obj == null || !obj.getClass().isArray()) throw new IllegalArgumentException("El argumento no es un array.");
		
		if (!lista.isEmpty()) {
				
			posicion = buscarPosicion(obj);
		}
			
		lista.add(posicion, obj);
	}
	
	/**
	 * Devuelve el array ubicado en la posición especificada de la lista.
	 * @param index
	 * @return el array en la ubicación deseada, o null si el índice se encuentra fuera del rango de la lista.
	 */
	public Object get(int index) {
		Object obj = null;
		
		if (index >= 0 && index < lista.size()) {
			
			obj = lista.get(index);
		}
		
		return obj;
	}
	
	/**
	 * Devuelve un entero con la posición en la lista de arrays, dependiendo de su tamaño.
	 * @param obj el objeto a analizar.
	 * @return posición en la lista.
	 */
	private int buscarPosicion(Object obj) {
		int posicion = 0;
		int celdasObjeto = obtenerCeldas(obj);
		
		int celdasObjetoLista; 
		
		do {
			celdasObjetoLista = obtenerCeldas(lista.get(posicion));
			posicion++;
		} while (celdasObjetoLista < celdasObjeto && posicion < lista.size());
		
		if (celdasObjetoLista >= celdasObjeto) posicion--;
		
		return posicion;
	}
	
	/**
	 * Devuelve un objeto Class con el tipo del array.
	 * @param obj
	 * @return tipo del array.
	 */
	private Class<?> obtenerTipo(Object obj) {
		Class<?> tipo = obj.getClass();
		
		while(tipo.isArray()) {
			tipo = tipo.getComponentType();
		}
		
		return tipo;
	}
	
	/**
	 * Devuelve un entero con la cantidad de celdas totales que contiene un array de cualquier tipo de dimensiones,
	 * siempre y cuando cada dimension contenga la misma cantidad de celdas.
	 * @param obj el array a analizar.
	 * @return cantidad de celdas.
	 */
	private int obtenerCeldas(Object obj) {
		int dimensiones;
		int celdasPorDimension;
		int celdasTotales;
		
		dimensiones = obtenerDimensiones(obj);
    	
    	celdasPorDimension = Array.getLength(obj);
    	
    	celdasTotales = (int) Math.pow(celdasPorDimension, dimensiones);
    	
    	return celdasTotales;
    }

	/**
	 * Devuelve un entero con la cantidad de dimensiones que contiene un array multidimensional.
	 * @param obj el array a analizar.
	 * @return cantidad de dimensiones.
	 */
	private int obtenerDimensiones(Object obj) {
		int dimensiones = 0;
		
		Class<?> tipo;
    	tipo = obj.getClass();
    	
    	while (tipo.isArray()) {
    		dimensiones++;
    		tipo = tipo.getComponentType();
    	}
    	
		return dimensiones;
	}

    public static void main(String[] args) {
    	ListArray la = new ListArray();
    	
    	la.agregarArray(new int[2][2][2]);   	// 8
    	la.agregarArray(new int[100][100]);		// 10000
    	la.agregarArray(new int[50]);			// 50
    	la.agregarArray(new double[200]);	    // 200
    	la.agregarArray(new char[5][5][5][5]);	// 625 
    	
	    la.mostrar();
    }
}