package jdbcExample.db;

import jdbcExample.Commands;
import jdbcExample.db.manager.TodoManager;
import jdbcExample.db.manager.UserManager;
import jdbcExample.model.Gender;
import jdbcExample.db.data.Todo;
import jdbcExample.db.data.User;
import jdbcExample.model.TodoStatus;

import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static TodoManager todoManager = new TodoManager();
    private static User generalUser;

    public static void main(String[] args) throws SQLException {
        boolean isrun = true;
        while (isrun) {
            Commands.printNoRegisterCommands();
            int command;
            command = Integer.parseInt(scanner.nextLine());
            switch (command) {
                case Commands.EXIT:
                    isrun = false;
                    break;
                case Commands.LOGIN:
                    loginUser();
                    break;
                case Commands.REGISTER:
                    registerUser();
            }
        }
    }

    private static void loginSucessfulCommands() throws SQLException {
        boolean isrun = true;
        while (isrun) {
            Commands.printLoginUserCommands();
            int command;
            command = Integer.parseInt(scanner.nextLine());
            switch (command) {
                case Commands.LOGOUT:
                    isrun = false;
                    break;
                case Commands.ADD_TODO:
                    addTodos();
                    break;
                case Commands.MY_LIST:
                    printAllTodos();
                    break;
                case Commands.MY_IN_PROGRESS_LIST:
                    printAllInProgressTodos();
                    break;
                case Commands.MY_FINISHED_LIST:
                    printAllFinishedTodos();
                    break;
                case Commands.CHANGE_TODO_STATUS:
                    changeTodoStatus();
                    break;
                case Commands.DELETE_TODO_BY_ID:
                    deleteTodoById();
                    break;
                default:
                    System.out.println("Wrong command");
                    break;

            }
        }
    }

    private static void deleteTodoById() throws SQLException {
        System.out.println("Please input todo id by delete");
        printAllTodos();
        String todoDataid = scanner.nextLine();
        todoManager.deleteTodoById(Integer.parseInt(todoDataid));
    }

    private static void changeTodoStatus() throws SQLException {
        System.out.println("Please input todo id by update");
        printAllTodos();
        String todoDataid = scanner.nextLine();
        System.out.println("Inser status " + TodoStatus.IN_PROGRESS + " or " + TodoStatus.FINISHED);
        String todoDataStatus = scanner.nextLine();
        todoManager.changeTodoStatus(Integer.parseInt(todoDataid), todoDataStatus, generalUser.getId());

    }

    private static void printAllFinishedTodos() throws SQLException {
        todoManager.printTodosByStatusInFinished(generalUser.getId());

    }

    private static void printAllInProgressTodos() throws SQLException {
        todoManager.printTodosByStatusInProgress(generalUser.getId());

    }

    private static void printAllTodos() throws SQLException {
        todoManager.printAllTodos(generalUser.getId());
    }


    private static void addTodos() {
        System.out.println("Please insert todo data (name,deadline(YY-MM-DD),");
        String todoData = scanner.nextLine();
        String[] todoDataStr = todoData.split(",");
        Todo todo = new Todo();
        todo.setName(todoDataStr[0]);
        todo.setDeadline(Date.valueOf(todoDataStr[1]));
        todo.setUserId(generalUser.getId());
        try {
            todoManager.addTodo(todo);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void loginUser() throws SQLException {
        System.out.println("Please input your phoneNumber");
        String loginUser = scanner.nextLine();
        User userByPhoneNumber = userManager.getUserByPhoneNumber(loginUser);
        if (userByPhoneNumber != null) {
            System.out.println("Welcome");
            generalUser = userByPhoneNumber;
            loginSucessfulCommands();
        } else {
            System.out.println("Incorrect phoneNumber");
        }

    }

    private static void registerUser() {
        System.out.println("Please input your data (name,surname,gender(MALE,FEMALE),age,phoneNumber,password");
        String userData = scanner.nextLine();
        String[] userDataStr = userData.split(",");
        try {
            User user = new User();
            user.setName(userDataStr[0]);
            user.setSurname(userDataStr[1]);
            user.setGender(Gender.valueOf(userDataStr[2].toUpperCase()));
            user.setAge(Integer.parseInt(userDataStr[3]));
            user.setPhone(userDataStr[4]);
            user.setPassword(userDataStr[5]);
            User userByPhoneNumber = userManager.getUserByPhoneNumber(userDataStr[4]);
            if (userByPhoneNumber != null) {
                System.out.println("This user is already in the database");
            } else {
                userManager.addUser(user);
                generalUser = user;
                loginSucessfulCommands();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("the data entry sequence was not followed: Please try again");
            registerUser();
        }
    }
}
