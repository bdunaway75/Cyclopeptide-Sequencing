import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CSP {
    // Define the masses and corresponding amino acids
    public static int[] AMINO_ACID_MASS = {57, 71, 87, 97, 99, 101, 103, 113, 114, 115, 128, 129, 131, 137, 147, 156, 163, 186};
    public static char[] AMINO_ACIDS = {'G','A','S','P','V','T','C','I','N','D','Q','E','M','H','F','R','Y','W'};

    // Store the main spectrum and amino acids
    ArrayList<Integer> MainSpectrum = new ArrayList<Integer>();
    ArrayList<Character> Acids = new ArrayList<Character>(Arrays.asList('G','A','S','P','V','T','C','I','N','D','Q','E','M','H','F','R','Y','W'));

    // Define the input file and the table for mapping mass to amino acid
    static File inputFile2 = new File("C:\\Users\\blake\\Sequencing\\CyclopeptideSequencing_data.txt");
    LinkedHashMap<Integer, Character> IMTable = new LinkedHashMap<>();

    public CSP() throws IOException {
        Scanner l = new Scanner(Path.of(inputFile2.toURI()));
        String input = "";

        // Create the mapping between mass and amino acid
        for(int i = 0; i < AMINO_ACIDS.length; i++){
            IMTable.put(AMINO_ACID_MASS[i], AMINO_ACIDS[i]);
        }

        while(l.hasNext()) {
            input = l.nextLine();
            System.out.println(input);
            String[] main = input.split(" ");

            // Convert input to integers and add them to the main spectrum
            for(String ele : main){
                MainSpectrum.add(Integer.valueOf(ele));
            }
        }
    }

    public void sequence(){
        ArrayList<String> Peptides = Expand(MainSpectrum);
        System.out.println(Peptides);
        ArrayList<ArrayList<Integer>> Spectrum = new ArrayList<>();

        while(!Peptides.isEmpty()){
            Spectrum = getSpectrum(Peptides);
            Peptides = trim(Spectrum, Peptides);
            Spectrum = getSpectrum(Peptides);
            Peptides = Check(Spectrum, Peptides);
            Peptides = Extend(Peptides);
        }
    }

    // Check if the spectrum matches the main spectrum
    public ArrayList<String> Check(ArrayList<ArrayList<Integer>> spectrum, ArrayList<String> Peptides){
        for(int i = 0; i < spectrum.size() ; i++) {
            ArrayList<Integer> sp = spectrum.get(i);

            if (sp.get(sp.size() - 1).equals(MainSpectrum.get(MainSpectrum.size() - 1))) {
                if(Cyclospectrum(Peptides.get(i)).equals(MainSpectrum)){
                    for(int j = 1; j < sp.size() - 1; j++){
                        System.out.print(sp.get(j));
                        if(j < sp.size() - 2) {
                            System.out.print("-");
                        }
                    }
                    System.out.print(" ");
                }
            }
        }
        return Peptides;
    }

    // Trim the candidates based on the spectrum
    public ArrayList<String> trim(ArrayList<ArrayList<Integer>> spectrum, ArrayList<String> candidates){
        for(ArrayList<Integer> spectru : spectrum){
            for(Integer mass : spectru) {
                if (!MainSpectrum.contains(mass)) {
                    candidates.remove(massTopeptide(spectru));
                    break;
                }
            }
        }
        return candidates;
    }

    // Convert mass to peptide string
    public String massTopeptide(ArrayList<Integer> spect){
        String peptide = "";
        for(int i = 1; i < spect.size() - 1; i++){
            peptide = peptide + IMTable.get(spect.get(i));
        }
        return peptide;
    }

    // Compute the cyclospectrum of a peptide
    public ArrayList<Integer> Cyclospectrum(String Peptide){
        ArrayList<String> Subpeptides = new ArrayList<>(CheckExtend(Peptide));
        ArrayList<Integer> Spectrum = new ArrayList<>();
        Spectrum.add(0);
        for(String subPep : Subpeptides){
            int mass = 0;
            for(int i = 0; i < subPep.length(); i++){
                mass = mass + AMINO_ACID_MASS[Acids.indexOf(subPep.charAt(i))];
            }
            Spectrum.add(mass);
        }
        Collections.sort(Spectrum);
        return Spectrum;
    }

    // Compute the spectrum for each peptide
    public ArrayList<ArrayList<Integer>> getSpectrum(ArrayList<String> peps){
        ArrayList<ArrayList<Integer>> spectrum = new ArrayList<>();

        for(String pep : peps) {
            ArrayList<Integer> spectru = new ArrayList<>();
            spectru.add(0);
            int mass = 0;

            for (int i = 0; i < pep.length(); i++) {
                for (int j = 0; j < AMINO_ACIDS.length; j++) {
                    if (pep.charAt(i) == AMINO_ACIDS[j]) {
                        mass = mass + AMINO_ACID_MASS[j];
                        spectru.add(AMINO_ACID_MASS[j]);
                        break;
                    }
                }
            }

            spectru.add(mass);
            spectrum.add(spectru);
        }

        return spectrum;
    }

    // Extend the peptides with additional amino acids
    public ArrayList<String> Extend(ArrayList<String> Pepetide){
        ArrayList<String> kmers = new ArrayList<>();

        for(String ele : Pepetide){
            for(int i = 0; i < AMINO_ACIDS.length; i++){
                String kmer = ele;

                for(int j = 0; j < Pepetide.get(0).length() ; j++){
                    if(i + j < AMINO_ACIDS.length && kmer.length() < Pepetide.get(0).length() + 1) {
                        kmer = kmer + AMINO_ACIDS[i + j];
                    }
                }

                kmers.add(kmer);
            }
        }

        return kmers;
    }

    // Generate all possible subpeptides of a peptide
    public ArrayList<String> CheckExtend(String Peptide){
        ArrayList<String> subPeps = new ArrayList<>();
        String loop = Peptide + Peptide;

        for(int i = 0; i < Peptide.length(); i++){
            String peptide = "";
            for(int j = 1; j < Peptide.length(); j++){
                peptide  = loop.substring(i, i + j);
                subPeps.add(peptide);
            }
        }
        subPeps.add(Peptide);
        return subPeps;
    }

    // Expand the spectrum to candidates based on mass
    public ArrayList<String> Expand(ArrayList<Integer> spectrum) {
        ArrayList<String> candidates = new ArrayList<>();

        for (Integer ele : spectrum) {
            for (int i = 0; i < AMINO_ACID_MASS.length; i++) {
                if (ele.equals(AMINO_ACID_MASS[i])) {
                    if (!candidates.contains(String.valueOf(IMTable.get(ele)))) {
                        candidates.add(String.valueOf(IMTable.get(ele)));
                    }
                }
            }
        }
        System.out.println(candidates);
        return Extend(candidates);
    }


    public static void main(String[] args) throws IOException {
        CSP r = new CSP();
        r.sequence();
    }
}