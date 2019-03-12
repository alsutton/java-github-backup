package com.alsutton.backup.github;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.TagOpt;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GitHubBackup {
    private RepositoryService repositoryService;
    private UsernamePasswordCredentialsProvider userCredentials;


    public GitHubBackup() {
        repositoryService = new RepositoryService();
    }

    void setCredentials(String username, String password) {
        repositoryService.getClient().setCredentials(username, password);
        userCredentials = new UsernamePasswordCredentialsProvider(username, password);
    }

    void backup(String source)
            throws IOException, GitAPIException {
        int ownerNameSeparator = source.indexOf('/');
        if (ownerNameSeparator == -1) {
            backupOrganisation(source);
        } else {
            Repository repository = repositoryService.getRepository(
                    source.substring(0, ownerNameSeparator), source.substring(ownerNameSeparator+1));
            backupRepository(repository);
        }
    }

    private void backupRepository(Repository repository)
        throws IOException, GitAPIException {
        File backupDirectory =
                new File(repository.getOwner().getLogin() + File.separatorChar + repository.getName());

        Logger.getAnonymousLogger()
                .log(Level.INFO,
                "Attempting to backup "+repository.getCloneUrl()+" to "+backupDirectory.getCanonicalPath());

        TransportCommand backupCommand = prepareCommand(backupDirectory, repository);
        if(userCredentials != null) {
            backupCommand.setCredentialsProvider(userCredentials);
        }
        backupCommand.call();
    }

    private TransportCommand prepareCommand(File backupDirectory, Repository repository) throws IOException {
        if(backupDirectory.exists()) {
            return Git
                    .open(backupDirectory)
                    .fetch()
                    .setTagOpt(TagOpt.FETCH_TAGS);
        }
        if(!backupDirectory.mkdirs()) {
            throw  new IOException("Unable to create directory at "+backupDirectory.getCanonicalPath());
        }
        return Git
                .cloneRepository()
                .setBare(true)
                .setURI(repository.getHtmlUrl())
                .setDirectory(backupDirectory);
    }

    private void backupOrganisation(String source)
            throws IOException, GitAPIException {
        for(Repository repository : repositoryService.getRepositories(source)) {
            backupRepository(repository);
        }
    }
}
