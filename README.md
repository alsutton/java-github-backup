# Pure Java GitHub Repo Backer-upper

I put this app together for a couple of reasons;

1) All my repos are on GitHub and it'd be nice to have local backups incase,
you know, the internet dissapears ;).
2) To get to learn [Bazel](https://bazel.build/) a bit better.

Feel free to try it out, submit PRs, dance a merry jig, etc.

## Compiling

1) Install Bazel
2) Clone this repository and go into the checkout directory
3) `bazel build //:GitHubBackup`

## Running

After compiling **change into the directory where you want the backup to be**,
then run

`(path to checkout dir)/bazel-bin/GitHubBackup org/repo org/repo ...`

e.g;

```$bash
cd ~/Backups
~/Projects/github-backup/bazel-bin/GitHubBackup alsutton/socialwindow
```

## Experimental stuff

#### Command Line Options

`-u` and `-p` allow you to specify a username and password to increase
the GitHub rate limit you might experience when using non-authenticated requests.

#### Org backups

Specifying `GitHubBackup org` instead of `org/repo` will iterate through the
available repositories and back them all up.