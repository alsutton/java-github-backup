package com.alsutton.backup.github;

import org.apache.commons.cli.*;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application to backup the repositories from GitHub
 */

public class Main {
    private static final String COMMAND_SYNTAX = "GitHubBackup [-u user] [-p password] org/repo (org/repo ...)";

    private static void performBackup(CommandLine commandLine) {
        GitHubBackup backupClient = new GitHubBackup();

        String user = commandLine.getOptionValue('u');
        if(user != null) {
            String password = commandLine.getOptionValue('p');
            backupClient.setCredentials(user, password);
        }

        for(String arg : commandLine.getArgs()) {
            try {
                backupClient.backup(arg);
            } catch (IOException | GitAPIException e) {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Problem backing up " + arg, e);
            }
        }
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("u", "user", true, "Username to connect with");
        options.addOption("p", "password", true, "Password for connecting user");

        if(args.length == 0) {
            new HelpFormatter().printHelp(COMMAND_SYNTAX, options);
            return;
        }

        try {
            Logger.getAnonymousLogger().log(Level.FINE, "Running in "+(new File(".").getCanonicalPath()));

            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);
            performBackup(commandLine);
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp(COMMAND_SYNTAX, options);
        }
    }
}
