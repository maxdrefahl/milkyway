import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.Collectors;
public class FileManager {
    public static void readFile(Planet sun, String fileName, ArrayList<Orbit> solarSystem){
        File file = new File(fileName);
        Scanner read;
        try {
            read = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File name not found.");
            return;
        }
        solarSystem.clear();
        HashMap<String, Orbit> parentMap = new HashMap<String, Orbit>();
        parentMap.put("Sun", sun);
        solarSystem.add(sun);
        while (read.hasNextLine()){
            String line = read.nextLine();
            String[] splitLine = line.split(",\t");
            if (splitLine[0].startsWith("//")){
                continue;
            }
            // Planet(String name, double mass, double rad, double a, double e, Orbit parent)
            // name, a, e, mass, parent
            Planet addPlanet = new Planet(splitLine[0], Double.parseDouble(splitLine[3]), 
                Double.parseDouble(splitLine[3]), Double.parseDouble(splitLine[1]),
                Double.parseDouble(splitLine[2]), parentMap.get(splitLine[4]));
            parentMap.put(addPlanet.getName(), addPlanet);
            solarSystem.add(addPlanet);
        }
        read.close();
    }
    public static void readFileStream(Planet sun, String fileName, ArrayList<Orbit> solarSystem){
        solarSystem.clear();
        HashMap<String, Orbit> parentMap = new HashMap<String, Orbit>();
        parentMap.put("Sun", sun);
        solarSystem.add(sun);
        List<String[]> list = new ArrayList();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            list = stream
                    .filter(line -> !line.startsWith("//"))
                    .map(line -> line.split(",\t"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        list.forEach(splitLine -> {
            Planet addPlanet = new Planet(splitLine[0], Double.parseDouble(splitLine[3]), 
                Double.parseDouble(splitLine[3]), Double.parseDouble(splitLine[1]),
                Double.parseDouble(splitLine[2]), parentMap.get(splitLine[4]));
            parentMap.put(addPlanet.getName(), addPlanet);
            solarSystem.add(addPlanet);
        });
    }
    public static void writeFile(String fileName, ArrayList<Orbit> solarSystem){
        File file = new File(fileName);
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
        } catch (Exception e) {
            System.out.println("File name not found");
            return;
        }
            // name, a, e, mass, parent
        StringBuilder builder = new StringBuilder(256);
        for (Orbit orbit : solarSystem) {
            builder.append(orbit.getName());
            builder.append(",\t");
            builder.append(orbit.getA());
            builder.append(",\t");
            builder.append(orbit.getE());
            builder.append(",\t");
            builder.append(orbit.getMass());
            builder.append(",\t");
            builder.append(orbit.getParent().getName());
            builder.append("\n");
        }
        writer.write(builder.toString());
        writer.close();
    }
}