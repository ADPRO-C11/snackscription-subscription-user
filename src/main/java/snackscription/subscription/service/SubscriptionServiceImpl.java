package snackscription.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.factory.SubscriptionFactory;
import snackscription.subscription.model.ShippingAddress;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private SubscriptionFactory subscriptionFactory = new SubscriptionFactory();

    @Override
    public Subscription save(SubscriptionDTO subscriptionDTO){
        Subscription subscription = convertDTOtoModel(subscriptionDTO);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<SubscriptionDTO> findAll(){
        return subscriptionRepository.findAll()
                .stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDTO findById(String id){
        if(id == null || id.isEmpty()){
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription isn't found"));

        return convertModelToDto(subscription);
    }

    @Override
    public Subscription update(SubscriptionDTO subscriptionDTO){
        if(subscriptionDTO == null){
            throw new IllegalArgumentException("Subscription cannot be null");
        }

        Subscription subscription = subscriptionRepository.findById(subscriptionDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription isn't found"));

        updateSubscription(subscription, subscriptionDTO);
        return subscriptionRepository.update(subscription);
    }

    @Override
    public void delete(String id){
        if(id == null || id.isEmpty()){
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if(subscriptionRepository.findById(id).isEmpty()){
            throw new IllegalArgumentException("Subscription not found");
        }
        subscriptionRepository.delete(id);
    }

    public SubscriptionDTO convertModelToDto(Subscription subscription){
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId(subscription.getId());
        subscriptionDTO.setUniqueCode(subscription.getUniqueCode());
        subscriptionDTO.setUserId(subscription.getUserId());
        subscriptionDTO.setSubscriptionBoxId(subscription.getSubscriptionBoxId());
        subscriptionDTO.setType(subscription.getType());
        subscriptionDTO.setStatus(subscription.getStatus());
        subscriptionDTO.setShippingAddress(subscription.getShippingAddress());
        subscriptionDTO.setDateCreated(subscription.getDateCreated());

        return subscriptionDTO;
    }

    public Subscription convertDTOtoModel(SubscriptionDTO subscriptionDTO){
        String type = subscriptionDTO.getType();
        String userId = subscriptionDTO.getUserId();
        String subscriptionBoxId = subscriptionDTO.getSubscriptionBoxId();
        ShippingAddress shippingAddress = subscriptionDTO.getShippingAddress();
        return subscriptionFactory.create(type, userId, subscriptionBoxId, shippingAddress);
    }

    public Subscription updateSubscription(Subscription subscription, SubscriptionDTO subscriptionDTO) {
        Optional.ofNullable(subscriptionDTO.getShippingAddress()).ifPresent(subscription::setShippingAddress);
        Optional.ofNullable(subscriptionDTO.getStatus()).ifPresent(subscription::setStatus);
        return subscription;
    }
}
