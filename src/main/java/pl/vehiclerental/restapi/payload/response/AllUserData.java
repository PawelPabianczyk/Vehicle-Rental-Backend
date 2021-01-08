package pl.vehiclerental.restapi.payload.response;

import pl.vehiclerental.restapi.dtos.PersonalInformationDto;
import pl.vehiclerental.restapi.dtos.UserDto;

public class AllUserData {

    private UserDto userDto;

    private PersonalInformationDto personalInformationDto;

    public AllUserData(UserDto userDto, PersonalInformationDto personalInformationDto) {
        this.userDto = userDto;
        this.personalInformationDto = personalInformationDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public PersonalInformationDto getPersonalInformationDto() {
        return personalInformationDto;
    }

    public void setPersonalInformationDto(PersonalInformationDto personalInformationDto) {
        this.personalInformationDto = personalInformationDto;
    }
}