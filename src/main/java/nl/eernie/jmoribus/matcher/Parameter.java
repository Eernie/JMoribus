package nl.eernie.jmoribus.matcher;

class Parameter {
    private final int start;
    private final int end;
    private final String whitespaceIfAny;

    public Parameter(int start, int end, String whitespaceIfAny) {
        this.start = start;
        this.end = end;
        this.whitespaceIfAny = whitespaceIfAny;
    }


    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getWhitespaceIfAny() {
        return whitespaceIfAny;
    }
}
