package com.habibian.tweeterclone.dto.mapper;

import com.habibian.tweeterclone.dto.UserDTO;
import com.habibian.tweeterclone.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting User entities to UserDTOs.
 */
public class UserDtoMapper {

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user The User entity to be converted.
     * @return The corresponding UserDTO.
     */
    public static UserDTO toUserDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .image(user.getImage())
                .backgroundImage(user.getBackgroundImage())
                .bio(user.getBio())
                .birthDate(user.getBirthDate())
                .followers(toUserDTOs(user.getFollowers()))
                .followings(toUserDTOs(user.getFollowings()))
                .login_with_google(user.isLogin_with_google())
                .location(user.getLocation())
//                .isVerified(false) TODO
                .build();
    }

    /**
     * Converts a list of User entities to a list of UserDTOs.
     *
     * @param users The list of User entities to be converted.
     * @return The corresponding list of UserDTOs.
     */
    public static List<UserDTO> toUserDTOs(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .fullname(user.getFullname())
                    .image(user.getImage())
                    .build();
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }
}
