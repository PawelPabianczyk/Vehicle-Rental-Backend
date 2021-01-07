package pl.vehiclerental.restapi.payload.response;

import pl.vehiclerental.restapi.dtos.PersonalInformationDto;
import pl.vehiclerental.restapi.dtos.UserDto;

import java.util.List;

public class AllUsersData {
    private List<UserDto> users;

    private List<PersonalInformationDto> personalInformationData;

    public AllUsersData(List<UserDto> users, List<PersonalInformationDto> personalInformationData) {
        this.users = users;
        this.personalInformationData = personalInformationData;
    }
}
