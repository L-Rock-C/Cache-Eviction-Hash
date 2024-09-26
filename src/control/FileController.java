package control;

import model.Cache;
import model.ServiceOrder;
import model.ServiceOrderServer;

import java.io.*;

public class FileController {

    public void WriteFile(File path, String input) throws IOException
    {
        BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(path) );
        bufferedWriter.append(input);
        bufferedWriter.close();
    }

    public ServiceOrderServer readSOFile(File path) throws IOException {
        ServiceOrderServer servicesOrders = new ServiceOrderServer(1);

        BufferedReader bufferedReader = new BufferedReader( new FileReader(path) );

        StringBuffer sbResult = new StringBuffer();
        String line = "";

        while (line != null)
        {
            sbResult.append(line + "\n");
            line = bufferedReader.readLine();

            if(line != null)
            {
                String[] SOData = line.split(";");
                int id = Integer.parseInt(SOData[0]);
                String name = SOData[1];
                String client = SOData[2];
                String description = SOData[3];
                long requestTime = Long.parseLong(SOData[4]);

                ServiceOrder serviceOrder = new ServiceOrder(id, name, client, description, requestTime);

                servicesOrders.insert(serviceOrder);
            }
        }

        return servicesOrders;
    }

    public Cache readCacheFile(File path) throws IOException {
        Cache cacheSO = new Cache();

        BufferedReader bufferedReader = new BufferedReader( new FileReader(path) );

        StringBuffer sbResult = new StringBuffer();
        String line = "";

        while (line != null)
        {
            sbResult.append(line + "\n");
            line = bufferedReader.readLine();

            if(line != null)
            {
                String[] SOData = line.split(";");
                int code = Integer.parseInt(SOData[0]);
                String name = SOData[1];
                String client = SOData[2];
                String description = SOData[3];
                long requestTime = Long.parseLong(SOData[4]);

                ServiceOrder serviceOrder = new ServiceOrder(code, name, client, description, requestTime);

                cacheSO.insert(serviceOrder);
            }
        }

        return cacheSO;
    }
}
