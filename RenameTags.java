package renameTags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RenameTags {
    /**
     * CONFIRA O SISTEMA arquitetura_ref
     * ele esta pegando os arquivos abaixo do diretorio de tags, tenta ver se tem como nao entrar recursivo
     * Se não, trata e pega so ate o dir de tags mesmo, ignora o que esta abaixo
     * 
     * malz não ter feito mais =/
     */
    private static String svnUrl = "http://srv-svn2/TCU/Fontes/java/libs-tcu/";

    public static void main(String[] args) throws IOException {
        Process proc = Runtime.getRuntime().exec("svn list " + svnUrl);

        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String s = null;
        // Itera pelos projetos
        while ((s = br.readLine()) != null) {
            System.out.println("==>" + s);
            List<String> changedPaths = new ArrayList<String>();
            String path = svnUrl + s + "tags";
            // Pega o log das tags mudadas
            proc = Runtime.getRuntime().exec("svn log " + path + " -qv -r {2015-06-23}:{2015-06-26}");

            BufferedReader br2 = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String s2 = null;
            // Itera pelas mudancas, adiciona o caminho mudado na lista
            while ((s2 = br2.readLine()) != null) {
                // PRINT COMENTADO SE QUISER CONFERIR AS DATAS
                // System.out.println(s2);
                if (s2.trim().startsWith("A")) {
                    changedPaths.add(s2);
                }
            }
            // A LISTA ESTA ORDENADA PELAS DATAS pela saida do comando
            if (!changedPaths.isEmpty()) {

                // To be remove - Ultimo elemento da lista
                System.out.println("rename->" + changedPaths.get(changedPaths.size() - 1));
                // To be renamed - Elementos anteriores
                for (int i = 0; i < (changedPaths.size() - 1); i++) {
                    System.out.println("remove->" + changedPaths.get(i));
                }
                System.out.println("******************************");
            }
            changedPaths.clear();
        }
    }
}
