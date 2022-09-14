package com.stp.shop.resource;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.stp.shop.domain.*;
import com.stp.shop.repo.BrandRepository;
import com.stp.shop.repo.CategoryRepository;
import com.stp.shop.repo.ColorRepository;
import com.stp.shop.repo.StakeHolderRepository;
import org.hibernate.criterion.Distinct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/staticDropdown")
public class StaticController {

    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    StakeHolderRepository stakeHolderRepository;

    @GetMapping(value = "/brands")
    public List<Brand> getAllBrands(){
        return brandRepository.findAllByOrderByNameAsc();
    }

    @GetMapping("/colors")
    public List<Color> getAllColors(){
        return colorRepository.findAllByOrderByNameAsc();
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return categoryRepository.findAllByOrderByNameAsc();
    }

    @GetMapping("/stakeHolders")
    public List<StakeHolder> getAllStackHolders(){
        return  stakeHolderRepository.findAll();
//        .stream()
//                .map(s -> new StakeHolderDTO(s.getId(), s.getName()))
//                .collect(Collectors.toList());
    }

    @GetMapping("/stakeHolders/distinct")
    public Set<StakeHolderDTO> getDistinctStackHolders(){
        return  stakeHolderRepository.findAll()
                .stream()
                .filter(distinctByKey(s -> s.getName()))
                .map( stakeHolder -> new StakeHolderDTO(stakeHolder.getId(),stakeHolder.getName()))
                .collect(Collectors.toSet());

    }

    @PostMapping("/stakeHolder/add")
    public StakeHolder addStackHolder(@RequestBody StakeHolder stakeHolder){
        return stakeHolderRepository.save(stakeHolder);
    }

    @PostMapping("/brand/add")
    public Brand addBrand(@RequestBody Brand brand){
        return brandRepository.save(brand);
    }

    @PostMapping("/color/add")
    public Color addColor(@RequestBody Color color){
        return colorRepository.save(color);
    }

    @PostMapping("/category/add")
    public Category addColor(@RequestBody Category category){
        return categoryRepository.save(category);
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
