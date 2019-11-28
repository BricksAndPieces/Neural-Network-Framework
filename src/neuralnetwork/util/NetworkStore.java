package neuralnetwork.util;

import neuralnetwork.core.NeuralNet;

import java.io.*;

public class NetworkStore {

    public static void writeNetworkToFile(NeuralNet net, File file) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(net);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuralNet getNeuralNetFromFile(File file) {
        final NeuralNet networkFromFile;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            networkFromFile = (NeuralNet) ois.readObject();
        }catch(IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }

        return networkFromFile;
    }

    public static void writeNetworkToFileOld(NeuralNet net, String fileName) {
        File file = new File(fileName);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(net);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuralNet getNeuralNetFromFileOLd(String fileName) {
        final NeuralNet networkFromFile;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            networkFromFile = (NeuralNet) ois.readObject();
        }catch(IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }

        return networkFromFile;
    }
}