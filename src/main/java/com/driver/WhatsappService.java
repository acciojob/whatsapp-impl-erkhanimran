package com.driver;

import java.util.HashMap;
import java.util.List;

public class WhatsappService {
    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name, String mobile) throws Exception {
        return whatsappRepository.createUser(name , mobile);
    }

    public Group createGroup(List<User> users) {
        return whatsappRepository.createGroup(users);
    }

    public int createMessage(String content) {
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        HashMap<Group,List<User>> groupListHashMap = whatsappRepository.getGroupUserMap();
        if(!groupListHashMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        if(!groupListHashMap.get(group).contains(sender)){
            throw new Exception("You are not allowed to send message");
        }

        return whatsappRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        HashMap<Group, User> groupUserHashMap = whatsappRepository.getAdminMap();
        if(!groupUserHashMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        if(groupUserHashMap.get(group) != approver){
            throw new Exception("Approver does not have rights");
        }


        HashMap<Group, List<User>> groupListHashMap = new HashMap<>();
        if(!groupListHashMap.get(group).contains(user)){
            throw new Exception("User is not a participant");
        }

        return whatsappRepository.changeAdmin(approver, user, group);
    }

    public int removeUser(User user) throws Exception {
        HashMap<Group, List<User>> groupListHashMap = new HashMap<>();
        Group currGroup = null;
        for(Group group : groupListHashMap.keySet()){
            if(groupListHashMap.get(group).contains(user)){
                currGroup = group;
            }
        }

        if(currGroup == null){
            throw new Exception("User not found");
        }

        HashMap<Group, User> adminMap = new HashMap<>();
        for(Group group : adminMap.keySet()){
            if(adminMap.get(group).equals(user)){
                throw new Exception("Cannot remove admin");
            }
        }

        return whatsappRepository.removeUser(currGroup, user);
    }
}
