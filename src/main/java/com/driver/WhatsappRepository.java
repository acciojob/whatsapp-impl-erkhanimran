package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    private HashMap<String, String> nameMobile;
    private HashMap<Integer, String> messageMap;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;

        this.nameMobile = new HashMap<>();
    }

    public HashMap<Group, List<User>> getGroupUserMap() {
        return groupUserMap;
    }

    public void setGroupUserMap(HashMap<Group, List<User>> groupUserMap) {
        this.groupUserMap = groupUserMap;
    }

    public HashMap<Group, List<Message>> getGroupMessageMap() {
        return groupMessageMap;
    }

    public void setGroupMessageMap(HashMap<Group, List<Message>> groupMessageMap) {
        this.groupMessageMap = groupMessageMap;
    }

    public HashMap<Message, User> getSenderMap() {
        return senderMap;
    }

    public void setSenderMap(HashMap<Message, User> senderMap) {
        this.senderMap = senderMap;
    }

    public HashMap<Group, User> getAdminMap() {
        return adminMap;
    }

    public void setAdminMap(HashMap<Group, User> adminMap) {
        this.adminMap = adminMap;
    }

    public HashSet<String> getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(HashSet<String> userMobile) {
        this.userMobile = userMobile;
    }

    public int getCustomGroupCount() {
        return customGroupCount;
    }

    public void setCustomGroupCount(int customGroupCount) {
        this.customGroupCount = customGroupCount;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public HashMap<String, String> getNameMobile() {
        return nameMobile;
    }

    public void setNameMobile(HashMap<String, String> nameMobile) {
        this.nameMobile = nameMobile;
    }

    public HashMap<Integer, String> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(HashMap<Integer, String> messageMap) {
        this.messageMap = messageMap;
    }

    public String createUser(String name, String mobile) throws Exception{
        if(nameMobile.containsKey(mobile)){
            throw new Exception();
        }
        nameMobile.put(mobile, name);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        Group group = new Group();
        if(users.size() == 2){
            group.setName(users.get(1).getName());
            group.setNumberOfParticipants(2);
        }
        else if(users.size() > 2){
            int count = customGroupCount+1;
            String countName = Integer.toString(count);
            group.setName("Group " + count);
            customGroupCount++;
            group.setNumberOfParticipants(users.size());
        }

        adminMap.put(group, users.get(0));
        groupUserMap.put(group, users);
        if(Objects.nonNull(group)){
            return group;
        }
        return null;
    }

    public int createMessage(String content) {
        int idOfCurrMessage = messageMap.size() + 1;
        messageMap.put(idOfCurrMessage, content);
        return idOfCurrMessage;
    }

    public int sendMessage(Message message, User sender, Group group) {
        groupMessageMap.get(group).add(message);
        senderMap.put(message, sender);
        return senderMap.size();
    }

    public String changeAdmin(User approver, User user, Group group) {
        adminMap.put(group, user);
        return "SUCCESS";
    }

    public int removeUser(Group group, User user) {
        groupUserMap.get(group).remove(user);

        for(Message message : senderMap.keySet()){
            if(senderMap.get(message) == user){
                senderMap.remove(message);
            }
            if(groupMessageMap.get(group).contains(message)){
                groupMessageMap.get(group).remove(message);
            }
        }

        return groupUserMap.get(group).size() + groupMessageMap.get(group).size() + senderMap.size();
    }
}
