package rw.ac.rca.gradesclassb.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.gradesclassb.models.MathLib;
import rw.ac.rca.gradesclassb.repositories.IMathLibRepository;
import rw.ac.rca.gradesclassb.services.MathLibServiceImpl;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MathLibServiceImplTest {
    @Mock
    private IMathLibRepository mathLibRepositoryMock;

    @InjectMocks
    private MathLibServiceImpl mathLibService;

    @Test
    public void save_shouldReturnMathLib() {
        MathLib mathLib = new MathLib();
        mathLib.setNumber1(2.0);
        mathLib.setNumber2(3.0);
        mathLib.setSum(5.0);
        mathLib.setProduct(6.0);
        Assert.assertEquals(mathLibService.save(2.0, 3.0).getSum(), mathLib.getSum());
    }

    @Test
    public void getAll_shouldReturnMathLibs() {
        when(mathLibRepositoryMock.findAll()).thenReturn(Arrays.asList(new MathLib(2.0, 3.0), new MathLib(4.5, 6.0)));
        Assert.assertEquals(mathLibService.getAll().size(), 2);
//        Assert.assertEquals(mathLibService.getAll().get(1).getSum(), Optional.of(10.5));
    }
}
