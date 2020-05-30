package jdbcExample;

public interface Commands {

    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;
    int LOGOUT = 3;
    int ADD_TODO = 4;
    int MY_LIST = 5;
    int MY_IN_PROGRESS_LIST = 6;
    int MY_FINISHED_LIST = 7;
    int CHANGE_TODO_STATUS = 8;
    int DELETE_TODO_BY_ID = 9;


    static void printNoRegisterCommands() {
        System.out.println("Insert " + EXIT + " for Exit ");
        System.out.println("Insert " + LOGIN + " for Login");
        System.out.println("Insert " + REGISTER + " for Register");

    }

    static void printLoginUserCommands(){
        System.out.println("Insert " + LOGOUT + " for Logout");
        System.out.println("Insert " + ADD_TODO + " for Add new todo");
        System.out.println("Insert " + MY_LIST + " for View your all todos");
        System.out.println("Insert " + MY_IN_PROGRESS_LIST + " for View your all progress todos");
        System.out.println("Insert " + MY_FINISHED_LIST + " for View your all finished todos");
        System.out.println("Insert " + CHANGE_TODO_STATUS + " for change your todos statuses");
        System.out.println("Insert " + DELETE_TODO_BY_ID + " for delete your todos by todo id");
    }
}
