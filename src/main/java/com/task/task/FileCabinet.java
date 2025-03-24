package com.task.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {
    private List<Folder> folders;

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return folderByName(folders, name);
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return foldersBySize(folders, size);
    }

    @Override
    public int count() {
        return countFolders(folders);
    }

    private Optional<Folder> folderByName(List<Folder> folders, String name) {
        for (Folder folder : folders) {
            if (folder.getName().equals(name)) {
                return Optional.of(folder);
            }
            if (folder instanceof MultiFolder) {
                Optional<Folder> present = folderByName(((MultiFolder) folder).getFolders(), name); // Here I double-check if in potential multiFolder, one of the folders isn't a multiFolder
                if (present.isPresent()) {
                    return present;
                }
            }
        }
        return Optional.empty();
    }

    private List<Folder> foldersBySize(List<Folder> folders, String size) {
        List<Folder> toReturn = new ArrayList<>();
        for (Folder folder : folders) {
            if (folder.getSize().equals(size)) {
                toReturn.add(folder);
            }
            if (folder instanceof MultiFolder) {
                toReturn.addAll(foldersBySize(((MultiFolder) folder).getFolders(), size));    // Same as in method above double-checking if folder from multiFolders isn't another multiFolder
            }
        }
        return toReturn;
    }

    private int countFolders(List<Folder> folders) {
        int count = 0;
        for (Folder folder : folders) {
            count++;
            if (folder instanceof MultiFolder) {
                count += countFolders(((MultiFolder) folder).getFolders());    // Making sure we count every folder in multiFolder and double-checking if there is present another multiFolder
            }
        }
        return count;
    }
}
