package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import Default.TrafficSimulator;
import Exceptions.ArgumentException;
import Exceptions.ErrorDeSimulacion;
import Exceptions.ItinerarioIncorrecto;
import Exceptions.NoExisteException;
import Exceptions.VehiculoNoPosible;
import Exceptions.YaExisteException;
import MVC.Controller;
import MVC.View;

public class Main {


	public enum ModoEjecucion {
		BATCH("batch"), GUI("gui");
		
		private String descModo;
		ModoEjecucion(String modeDesc) {
			descModo = modeDesc ;
		}
		public String getModelDesc() {
			return descModo;
		}
	
	}
	
	private final static Integer limiteTiempoPorDefecto = 10;
	private final static String modoPorDefecto = "batch";
	private static Integer limiteTiempo = null;
	private static String ficheroEntrada = null;
	private static String ficheroSalida = null;
	private static ModoEjecucion modo = null;
	

	
	private static void ParseaArgumentos(String[] args) {

		// define the valid command line options
		//
		Options opcionesLineaComandos = Main.construyeOpciones();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine linea = parser.parse(opcionesLineaComandos, args);
			parseaOpcionModo(linea);
			if(modo.getModelDesc().equals("batch")) {
				parseaOpcionHELP(linea, opcionesLineaComandos);
				parseaOpcionFicheroIN(linea);
				parseaOpcionFicheroOUT(linea);
				parseaOpcionSTEPS(linea);
			}
			else {
				parseaOpcionFicheroINaux(linea);
			}
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] resto = linea.getArgs();
			if (resto.length > 0) {
				String error = "Illegal arguments:";
				for (String o : resto)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException | ErrorDeSimulacion e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options construyeOpciones() {
		Options opcionesLineacomandos = new Options();

		opcionesLineacomandos.addOption(Option.builder("h").longOpt("help").desc("Muestra la ayuda.").build());
		opcionesLineacomandos.addOption(Option.builder("i").longOpt("input").hasArg().desc("Fichero de entrada de eventos.").build());
		
		//
		opcionesLineacomandos.addOption(Option.builder("m").longOpt("mode").hasArg().desc(" 'batch' for batch mode and 'gui' for GUI mode").build());
		//
		opcionesLineacomandos.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Fichero de salida, donde se escriben los informes.").build());
		opcionesLineacomandos.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Pasos que ejecuta el simulador en su bucle principal (el valor por defecto es " + Main.limiteTiempoPorDefecto + ").")
				.build());

		
		return opcionesLineacomandos;
	}

	private static void parseaOpcionHELP(CommandLine linea, Options opcionesLineaComandos) {
		if (linea.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), opcionesLineaComandos, true);
			System.exit(0);
		}
	}

	private static void parseaOpcionFicheroIN(CommandLine linea) throws ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
		if (Main.ficheroEntrada == null) {
			throw new ParseException("El fichero de eventos no existe");
		}
	}
	
	private static void parseaOpcionFicheroINaux(CommandLine linea) throws ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
		
	}

	private static void parseaOpcionFicheroOUT(CommandLine linea) throws ParseException {
		Main.ficheroSalida = linea.getOptionValue("o");
	}

	private static void parseaOpcionSTEPS(CommandLine linea) throws ParseException {
		String t = linea.getOptionValue("t", Main.limiteTiempoPorDefecto.toString());
		try {
			Main.limiteTiempo = Integer.parseInt(t);
			assert (Main.limiteTiempo < 0);
		} catch (Exception e) {
			throw new ParseException("Valor invalido para el limite de tiempo: " + t);
		}
	}
	
	private static void parseaOpcionModo(CommandLine linea) throws ErrorDeSimulacion {
		String s = linea.getOptionValue("m", Main.modoPorDefecto);
		
		if (s.equals("batch")) {
			modo = ModoEjecucion.BATCH;
		}
		else
			modo = ModoEjecucion.GUI;
	}

	private static void iniciaModoEstandar() throws IOException, ArgumentException, ItinerarioIncorrecto, YaExisteException, NoExisteException, VehiculoNoPosible, ErrorDeSimulacion {
		InputStream is = new FileInputStream(new File(Main.ficheroEntrada));
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim,Main.limiteTiempo,is,os);
		ctrl.run();
		is.close();
		System.out.println("Done!");

	}
	
	private static void iniciaModografico() throws FileNotFoundException, InvocationTargetException, InterruptedException{
		
		TrafficSimulator sim = new TrafficSimulator();
		InputStream is = null;

		if(Main.ficheroEntrada != null) {
			is = new FileInputStream(new File(Main.ficheroEntrada));
			
		}
		
		Controller ctrl = new Controller(sim, 0, is ,System.out);
		
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					new View(sim, Main.ficheroEntrada, ctrl);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
	}

	public static void main(String[] args) throws IOException, ArgumentException, ItinerarioIncorrecto, YaExisteException, NoExisteException, VehiculoNoPosible, ErrorDeSimulacion, InvocationTargetException, InterruptedException {

		// example command lines:
		//
		// -i resources/examples/events/basic/ex1.ini
		// -i resources/examples/events/advanced/ex1.ini
		// --help
		//
		
		Main.ParseaArgumentos(args);
		if (modo.getModelDesc().equals("batch")) {
			iniciaModoEstandar();
		}
		else if (modo.getModelDesc().equals("gui")) {
			iniciaModografico();
		}
		
		
		
		//Si pones esto ejecuta todos los .ini de la direccion

		//Main.ejecutaFicheros("C:\\Users\\Joy\\OneDrive\\LocalGit\\Practica4\\advanced");
		
		//Si pones esto ejecuta solo el .ini que pase por argumentos
	}
	
	@SuppressWarnings("unused")
	private static void ejecutaFicheros(String path) throws IOException, ArgumentException, ItinerarioIncorrecto, YaExisteException, NoExisteException, VehiculoNoPosible, ErrorDeSimulacion {

		File dir = new File(path);

		if ( !dir.exists() ) {
			throw new FileNotFoundException(path);
		}
		
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});

		for (File file : files) {
			Main.ficheroEntrada = file.getAbsolutePath();
			Main.ficheroSalida = file.getAbsolutePath() + ".out";
			Main.limiteTiempo = 10;
			Main.iniciaModoEstandar();
		}

	}

}
