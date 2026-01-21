public enum Commands {
    SUBJECT ("subject - Defines the new subject and its components, and saves the current subject if defined\n","subject [number of components] [subject name]"),
    SAVE ("save - Saves the current subject to a text file\n","save"),
    LOAD ("load - Load a subject from a text file\n","load [subject name]"),
    STUDENT ("student - Adds a student\n","student [student number] [student name]"),
    GRADE ("grade - Submits a student's grade\n","grade [student number] / grade [student name]"),
    CHECK ("check - Checks a student's assignment grades\n","check [student name] / check [student number]"),
    LIST ("list - Lists all grades of an assigment or the full grades sheet\n","list [assignment name] / list"),
    HELP ("help - Shows the available commands and their usage\n","help"),
    EXIT ("exit - Terminates the execution of the program and saves the current subject if defined\n","exit"),
    EXAM ("exam - Shows the students automatically registered for the exam\n","exam"),
    UNKNOWN("","");

    private final String desc;

    private final String usage;

    Commands(String description, String usage) {
        this.desc = description;
        this.usage = usage;
    }

    public String getDesc(){
        return desc;
    }

    public String getUsage(){
        return usage;
    }
}
