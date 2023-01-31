package com.devonfw.sample.archunit.batch;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SampleTaskImport {
    public static void main(String[] args) {

        if ( args == null || args.length< 3 )
        {
            System.err.println("Container id as first, dumpfilename as second and postgre-username as third argument required");
            System.exit(1);
        }
        String containerId = args[0];
        String filename = args[1]; 
        String username = args[2];
        try {
            sampleTaskImport(containerId, filename, username);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
  
    }

    private static void sampleTaskImport(String containerId, String filename, String username) throws Exception {
        try {
            Runtime rt = Runtime.getRuntime();
            String dockerCopyCommand = String.format("docker cp %s %s:/var/lib/postgresql/data", filename, containerId);
            Process pr = rt.exec("cmd /c "+ dockerCopyCommand);

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line=null;

            while((line=input.readLine()) != null) {
                System.out.println(line);
            }

            int exitVal = pr.waitFor();
            System.out.println("Exited with error code "+ exitVal);
            if(exitVal == 0) {
                String pg_restoreCommand = String.format("docker exec %s pg_restore -U %s -d quarkus --clean /var/lib/postgresql/data/%s", containerId, username, filename);
                Process pr2 = rt.exec("cmd /c "+ pg_restoreCommand);
                BufferedReader input2 = new BufferedReader(new InputStreamReader(pr.getInputStream()));

                String line2 = null;

                while((line2 = input2.readLine()) != null) {
                    System.out.println(line2);
                }
                int exitVal2 = pr2.waitFor();
                System.out.println("Exited with error code " + exitVal2);

            } else {
                System.out.println("Import failed. Could not copy data to docker mount");
            }

        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

}
