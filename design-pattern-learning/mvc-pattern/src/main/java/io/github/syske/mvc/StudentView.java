/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.mvc;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-21 22:01
 */
public class StudentView {
    public void printStudentDetails(String studentName, String ideNo){
        System.out.println("Student: ");
        System.out.println("Name: " + studentName);
        System.out.println("id No: " + ideNo);
    }
}
