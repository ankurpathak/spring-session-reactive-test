package com.ankurpathak.springsessionreactivetest;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.util.Optional;

@Service
public class MaxmindGeoIp2Service implements IpService {
    @Autowired
    private DatabaseReader databaseReader;


    @Override
    public Mono<String> ipToCountryAlphaCode(String ip) {
        return AsyncUtil.async(() -> {
            try {
                InetAddress ipAddress = InetAddress.getByName(ip);
                CityResponse response = databaseReader.city(ipAddress);
                return response.getCountry().getIsoCode();
            } catch (Exception ex) {
                return Params.IN;
            }
        });
    }
}
