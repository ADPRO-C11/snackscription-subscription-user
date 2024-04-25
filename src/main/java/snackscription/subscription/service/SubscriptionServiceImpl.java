package snackscription.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snackscription.subscription.dto.DTOMapper;
import snackscription.subscription.dto.SubscriptionDTO;
import snackscription.subscription.model.Subscription;
import snackscription.subscription.repository.SubscriptionRepository;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription save(SubscriptionDTO subscriptionDTO){
        Subscription subscription = DTOMapper.convertDTOtoModel(subscriptionDTO);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<SubscriptionDTO> findAll(){
        return subscriptionRepository.findAll()
                .stream()
                .map(DTOMapper::convertModelToDto)
                .toList();
    }

    @Override
    public SubscriptionDTO findById(String id){
        if(id == null || id.isEmpty()){
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription isn't found"));

        return DTOMapper.convertModelToDto(subscription);
    }

    @Override
    public Subscription update(SubscriptionDTO subscriptionDTO){
        if(subscriptionDTO == null){
            throw new IllegalArgumentException("Subscription cannot be null");
        }

        Subscription subscription = subscriptionRepository.findById(subscriptionDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription isn't found"));

        DTOMapper.updateSubscription(subscription, subscriptionDTO);
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
}
