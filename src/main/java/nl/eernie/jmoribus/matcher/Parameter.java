package nl.eernie.jmoribus.matcher;

class Parameter {
    private final int start;
    private final int end;
    private final String whitespaceIfAny;
    private final String name;

    public Parameter(String pattern, int start, int end,
                     String whitespaceIfAny, String prefix) {
        this.start = start;
        this.end = end;
        this.whitespaceIfAny = whitespaceIfAny;
        this.name = pattern.substring(start + prefix.length(),
                end - whitespaceIfAny.length()).trim();
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

    public String getName() {
        return name;
    }
}