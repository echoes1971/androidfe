package it.rra.fe.engine;

import it.rra.fe.LogBean;

public class KaosSequence {

    /** Holds value of property element. */
    private char[] element;

    /** Holds value of property length. */
    private int length;

    /** Holds value of property maxSize. */
    private int maxSize;

    private LogBean log;
    /** Creates new KaosSequence */
    public KaosSequence(int elements) {
        maxSize = elements;
        length = 0;
        log = LogBean.getInstance();
        element = new char[elements];
        for (int i=0 ; i<elements ; i++) element[i] = '\0';
    }

    // Utilita'
    public boolean isValidElement(char element) {
        // Controllo che sia un numero
        int j = (int) element;
        log.writeln(LogBean.DEBUG, "KaosSequence.isValidElement: \'"+element+"\' = "+j+"\'." );
        return ( (j>=((int)'0')) && (j<=((int)'9')) );
    }
    public String toString() {
        return (new String( element )).substring(0,length);
    }

    // Getters & Setters
    public char getElement(int index) {        return element[index];    }
    public void setElement(int index,char element) {        this.element[index] = element;    }

    public void setAllElements(String sequence) {
        setAllElements( sequence.toCharArray() );
    }
    public void setAllElements(char[] sequence) {
        log.write(LogBean.DEBUG, "KaosSequence.setAllElements: sequence="+sequence);
        length = 0;
        try {
            while( addElement( sequence[ length ] ) ) {}
        } catch(Exception e) {
            // Ho finito gli elementi nell'array in input
        }
    }
    /**Aggiunge un elemento in coda. Torna TRUE sse l'array non Ë pieno. */
    public boolean addElement(char element) {
        log.writeln(LogBean.DEBUG, "KaosSequence.addElement: length="+length+" - maxsize="+maxSize);
        if ( (length < maxSize ) && isValidElement(element) ) {
            this.element[length++] = element;
            return true;
        } else
            return false;
    }

    public int getLength() {        return length+1;    }
    public void setLength(int length) {        this.length = length;    }

    public int getMaxSize() {        return maxSize;    }
}
