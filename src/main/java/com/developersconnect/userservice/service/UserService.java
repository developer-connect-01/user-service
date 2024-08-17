package com.developersconnect.userservice.service;

import com.developersconnect.userservice.dto.*;
import com.developersconnect.userservice.model.*;
import com.developersconnect.userservice.model.enums.Role;
import com.developersconnect.userservice.repository.UserRepository;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final static String USER_NOT_FOUND_MSG = "user with username %s not found";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String RANDOM_USER_API_URL = "https://randomuser.me/api/";

    public List<User> fetchAndSaveUserResponse() {
        logger.info("Fetching random user data from API");
        String jsonString = restTemplate.getForObject(RANDOM_USER_API_URL, String.class);
        logger.debug("Fetched JSON string: {}", jsonString);
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("results");

            List<User> users = new ArrayList<>();
            List<UserDTO> results = mapUserDTO(jsonArray);
            for (UserDTO userDTO : results) {
                User user = new User();
                mapUser(userDTO, user);
                if(user.getRole() == null){
                    user.setRole(Role.USER);
                }
                users.add(userRepository.save(user));
            }
            return users;
        } else {
            throw new JsonParseException("Expected JSON object");
        }
    }

    List<UserDTO> mapUserDTO(JsonArray jsonArray){
        List<UserDTO> results = new ArrayList<UserDTO>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        for(int i = 0; i < jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            UserDTO userDTO = new UserDTO();
            userDTO.setGender(jsonObject.get("gender").getAsString());
            userDTO.setTitle(jsonObject.get("name").getAsJsonObject().get("title").getAsString());
            userDTO.setFirstName(jsonObject.get("name").getAsJsonObject().get("first").getAsString());
            userDTO.setLastName(jsonObject.get("name").getAsJsonObject().get("last").getAsString());

            LocationDTO locationDTO = new LocationDTO();
            StreetDTO streetDTO = new StreetDTO();
            streetDTO.setName(jsonObject.get("location").getAsJsonObject().get("street").getAsJsonObject().get("name").getAsString());
            streetDTO.setNumber(jsonObject.get("location").getAsJsonObject().get("street").getAsJsonObject().get("number").getAsInt());
            locationDTO.setStreetDTO(streetDTO);
            locationDTO.setCity(jsonObject.get("location").getAsJsonObject().get("city").getAsString());
            locationDTO.setState(jsonObject.get("location").getAsJsonObject().get("state").getAsString());
            locationDTO.setCountry(jsonObject.get("location").getAsJsonObject().get("country").getAsString());
            locationDTO.setPostcode(jsonObject.get("location").getAsJsonObject().get("postcode").getAsString());
            locationDTO.setLatitude(jsonObject.get("location").getAsJsonObject().get("coordinates").getAsJsonObject().get("latitude").getAsString());
            locationDTO.setLongitude(jsonObject.get("location").getAsJsonObject().get("coordinates").getAsJsonObject().get("longitude").getAsString());

            userDTO.setLocationDTO(locationDTO);
            userDTO.setEmail(jsonObject.get("email").getAsString());
            userDTO.setUsername(jsonObject.get("login").getAsJsonObject().get("username").getAsString());

            userDTO.setEmail(jsonObject.get("email").getAsString());
            userDTO.setPassword(jsonObject.get("login").getAsJsonObject().get("password").getAsString());
            userDTO.setSalt(jsonObject.get("login").getAsJsonObject().get("salt").getAsString());
            userDTO.setMd5(jsonObject.get("login").getAsJsonObject().get("md5").getAsString());
            userDTO.setSha1(jsonObject.get("login").getAsJsonObject().get("sha1").getAsString());
            userDTO.setSha256(jsonObject.get("login").getAsJsonObject().get("sha256").getAsString());

            try {
                userDTO.setDob(dateFormat.parse(jsonObject.get("dob").getAsJsonObject().get("date").getAsString()));
                userDTO.setRegistered(dateFormat.parse(jsonObject.get("registered").getAsJsonObject().get("date").getAsString()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            userDTO.setPhone(jsonObject.get("phone").getAsString());
            userDTO.setCell(jsonObject.get("cell").getAsString());

            PictureDTO pictureDTO = new PictureDTO();
            pictureDTO.setLarge(jsonObject.get("picture").getAsJsonObject().get("large").getAsString());
            pictureDTO.setMedium(jsonObject.get("picture").getAsJsonObject().get("medium").getAsString());
            pictureDTO.setThumbnail(jsonObject.get("picture").getAsJsonObject().get("thumbnail").getAsString());
            userDTO.setPictureDTO(pictureDTO);

            userDTO.setNat(jsonObject.get("nat").getAsString());

            results.add(userDTO);
        }

        return results;
    }

    void mapUser(UserDTO userDTO, User user){
        user.setGender(userDTO.getGender());
        user.setTitle(userDTO.getTitle());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setSalt(userDTO.getSalt());
        user.setMd5(userDTO.getMd5());
        user.setSha1(userDTO.getSha1());
        user.setSha256(userDTO.getSha256());
        user.setDob(userDTO.getDob());
        user.setRegistered(userDTO.getRegistered());
        user.setPhone(userDTO.getPhone());
        user.setCell(userDTO.getCell());
        user.setPicture(setPicture(userDTO.getPictureDTO()));
        user.setNat(userDTO.getNat());
        user.setLocation(setLocation(userDTO.getLocationDTO()));
    }

    Picture setPicture(PictureDTO pictureDTO){
        Picture picture = new Picture();
        picture.setLarge(pictureDTO.getLarge());
        picture.setMedium(pictureDTO.getMedium());
        picture.setThumbnail(pictureDTO.getThumbnail());
        return picture;
    }

    Location setLocation(LocationDTO locationDTO){
        Location location = new Location();
        Street street = new Street();
        street.setName(locationDTO.getStreetDTO().getName());
        street.setNumber(locationDTO.getStreetDTO().getNumber());
        location.setStreet(street);
        location.setCity(locationDTO.getCity());
        location.setState(locationDTO.getState());
        location.setCountry(locationDTO.getCountry());
        location.setPostcode(locationDTO.getPostcode());
        location.setLongitude(locationDTO.getLongitude());
        location.setLatitude(locationDTO.getLatitude());
        return location;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }
}
