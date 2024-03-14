package projet.vlille;
import projet.vlille.exception.OutOfServiceException;
import projet.vlille.exception.StationEmptyException;
import projet.vlille.exception.StationFullException;
import projet.vlille.program.Program;

public class Main {

    public static void main(String[] args) throws StationFullException, StationEmptyException, OutOfServiceException {
        Program program = new Program(3,6);
        program.Run();
    }
}