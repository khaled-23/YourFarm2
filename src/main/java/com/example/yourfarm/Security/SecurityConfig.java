package com.example.yourfarm.Security;

import com.example.yourfarm.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()

                //Auth
                .requestMatchers("/api/v1/auth/users").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/register").permitAll()
                .requestMatchers("/api/v1/auth/Update").hasAnyAuthority("CUSTOMER" ,"COMPANY","FARM","FARMER","SPECIALIST")
                .requestMatchers("/api/v1/auth/delete/{user_id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/logOut").permitAll()

                //Customer
                .requestMatchers("/api/v1/customer/customers").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/register").permitAll()
                .requestMatchers("/api/v1/customer/update").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/delete/").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/search-farmer-near").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/customer/search-farms-near").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/customer/search-specialists-near").hasAnyAuthority("COMPANY","CUSTOMER")



                //Company
                .requestMatchers("/api/v1/company/companies").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/company/register").permitAll()
                .requestMatchers("/api/v1/company/update/").hasAuthority("COMPANY")
                .requestMatchers("/api/v1/company/delete/").hasAuthority("ADMIN")



                //Contract
                .requestMatchers("/api/v1/contract/contract").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/contract/create-contract").hasAuthority("COMPANY")
                .requestMatchers("/api/v1/contract/new-contract").hasAuthority("FARM")
                .requestMatchers("/api/v1/contract/accept-contract").hasAuthority("FARM")
                .requestMatchers("/api/v1/contract/current-contract").hasAnyAuthority("COMPANY","FARM")
                .requestMatchers("/api/v1/contract/previous-contract").hasAnyAuthority("COMPANY","FARM")
                .requestMatchers("/api/v1/contract/cancel/").hasAuthority("COMPANY")


                //Coupon
                .requestMatchers("/api/v1/coupon/coupons").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/coupon/add/").hasAnyAuthority("COMPANY","CUSTOMER")

                //Plant
                .requestMatchers("/api/v1/plant/get").permitAll()
                .requestMatchers("/api/v1/plant/add").hasAuthority( "FARM")
                .requestMatchers("/api/v1/plant/update/").hasAuthority( "FARM")
                .requestMatchers("/api/v1/plant/delete").hasAuthority("FARM")
                .requestMatchers("/api/v1/plant/view-plant-of-farm/").permitAll()
                .requestMatchers("/api/v1/plant/find-plant-by-name/").permitAll()
                .requestMatchers("/api/v1/plant/find-plant-by-type/").permitAll()



                //Farm
                .requestMatchers("/api/v1/farm/farms").permitAll()
                .requestMatchers("/api/v1/farm/register").permitAll()
                .requestMatchers("/api/v1/farm/update").hasAuthority("FARM")
                .requestMatchers("/api/v1/farm/delete").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/farm/comments/").permitAll()
                .requestMatchers("/api/v1/farm/get-best-evaluation").permitAll()
                .requestMatchers("/api/v1/farm/get-best-sales").permitAll()


                //Farmer
                .requestMatchers("/api/v1/farmer/farmers").permitAll()
                .requestMatchers("/api/v1/farmer/register").permitAll()
                .requestMatchers("/api/v1/farmer/update").hasAuthority("FARMER")
                .requestMatchers("/api/v1/farmer/delete").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/farmer/comments/").permitAll()
                .requestMatchers("/api/v1/farmer/get-best-evaluation").permitAll()


                //Specialist
                .requestMatchers("/api/v1/specialist/get").permitAll()
                .requestMatchers("/api/v1/specialist/add").permitAll()
                .requestMatchers("/api/v1/specialist/update/").hasAuthority( "SPECIALIST")
                .requestMatchers("/api/v1/specialist/delete").hasAuthority( "ADMIN")
                .requestMatchers("/api/v1/specialist/comments/").permitAll()
                .requestMatchers("/api/v1/specialist/getBestEvaluation").permitAll()

                //OrderPlant
                .requestMatchers("/api/v1/order-plant/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/order-plant/order-plant").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-plant/order-plant-cancel/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-plant/new-plant-orders").hasAuthority("FARM")
                .requestMatchers("/api/v1/order-plant/current-plant-orders").hasAnyAuthority("COMPANY","CUSTOMER","FARM")
                .requestMatchers("/api/v1/order-plant/previous-plant-orders").hasAnyAuthority("COMPANY","CUSTOMER","FARM")
                .requestMatchers("/api/v1/order-plant/update-order-plant/").hasAuthority("FARM")
                .requestMatchers("/api/v1/order-plant/accept-order-plant/").hasAuthority("FARM")
                .requestMatchers("/api/v1/order-plant/reject-order-plant/").hasAuthority("FARM")
                .requestMatchers("/api/v1/order-plant/comment/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-plant/evaluation-on-order-plant/").hasAnyAuthority("COMPANY","CUSTOMER")




                //OrderFarmer
                .requestMatchers("/api/v1/order-farmer/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/order-farmer/request-farmer/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-farmer/order-farmer-cancel/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-farmer/new-orders-to-farmer").hasAuthority("FARMER")
                .requestMatchers("/api/v1/order-farmer/current-orders").hasAnyAuthority("COMPANY","CUSTOMER","FARMER")
                .requestMatchers("/api/v1/order-farmer/previous-orders").hasAnyAuthority("COMPANY","CUSTOMER","FARMER")
                .requestMatchers("/api/v1/order-farmer/accept-order-farmer/").hasAuthority("FARMER")
                .requestMatchers("/api/v1/order-farmer/reject-order-farmer/").hasAuthority("FARMER")
                .requestMatchers("/api/v1/order-farmer/update-order-farmer/").hasAuthority("FARMER")
                .requestMatchers("/api/v1/order-farmer/comment/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-farmer/evaluation-on-order-farmer/").hasAnyAuthority("COMPANY","CUSTOMER")



                //OrderGuidance
                .requestMatchers("/api/v1/order-guidance/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/order-guidance/request-guidance/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-guidance/order_guidance_cancel/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-guidance/new-orders-to-specialist").hasAuthority("SPECIALIST")
                .requestMatchers("/api/v1/order-guidance/current-orders").hasAnyAuthority("COMPANY","CUSTOMER","SPECIALIST")
                .requestMatchers("/api/v1/order-guidance/previous-orders").hasAnyAuthority("COMPANY","CUSTOMER","SPECIALIST")
                .requestMatchers("/api/v1/order-guidance/accept-order-guidance/").hasAuthority("SPECIALIST")
                .requestMatchers("/api/v1/order-guidance/reject-order-guidance/").hasAuthority("SPECIALIST")
                .requestMatchers("/api/v1/order-guidance/update-order-guidance/").hasAuthority("SPECIALIST")
                .requestMatchers("/api/v1/order-guidance/comment/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-guidance/evaluation-on-order-guidance/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-guidance/get-list-order-guidance/").hasAnyAuthority("COMPANY","CUSTOMER")
                .requestMatchers("/api/v1/order-guidance/get-order-guidance/").hasAnyAuthority("COMPANY","CUSTOMER")


                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}