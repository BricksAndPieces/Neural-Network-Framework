package neuralnetwork.util;

import neuralnetwork.core.NeuralNet;

import java.io.*;

public class NetworkStore {

    public static void writeNetworkToFile(NeuralNet net, String fileName) {
        File file = new File(fileName);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(net);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static NeuralNet getNeuralNetFromFile(String fileName) {
        final NeuralNet networkFromFile;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            networkFromFile = (NeuralNet) ois.readObject();
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return networkFromFile;
    }
}