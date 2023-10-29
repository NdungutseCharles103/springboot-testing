package rw.ac.rca.gradesclassb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.rca.gradesclassb.models.MathLib;
import rw.ac.rca.gradesclassb.repositories.IMathLibRepository;

import java.util.List;

@Service
public class MathLibServiceImpl {
    private final IMathLibRepository mathLibRepository;

    public MathLibServiceImpl(IMathLibRepository mathLibRepository) {
        this.mathLibRepository = mathLibRepository;
    }

    public MathLib save(Double number1, Double number2) {
        MathLib mathLib = new MathLib();
        mathLib.setNumber1(number1);
        mathLib.setNumber2(number2);
        mathLibRepository.save(mathLib);
        mathLib.setSum(number1 + number2);
        mathLib.setProduct(number1 * number2);
        return mathLib;
    }

    public List<MathLib> getAll() {
        return mathLibRepository.findAll();
    }
}
