package services;

import dao.AppRoleRepository;
import dao.AppUserRepository;
import entities.AppRole;
import entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //BCryptPasswordEncoder bCryptPasswordEncoder;
//    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.appUserRepository = appUserRepository;
//        this.appRoleRepository = appRoleRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {
        AppUser user= appUserRepository.findByUsername(username);
        if(user!=null) throw new RuntimeException("User already exists");
        if(!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setActivated(true);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUserRepository.save(appUser);
        addRoleToUser(username,"USER");
        return appUser;
    }

    @Override
    public AppRole save(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {

        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(rolename);
        appUser.getRoles().add(appRole);
    }
}
