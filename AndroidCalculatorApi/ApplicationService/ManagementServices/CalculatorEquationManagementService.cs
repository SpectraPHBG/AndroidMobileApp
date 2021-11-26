using ApplicationService.DTOs;
using Data_Layer.Context;
using Data_Layer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApplicationService.ManagementServices
{
    public class CalculatorEquationManagementService
    {
        public List<CalculatorEquationDTO> GetAllEquations()
        {
            List<CalculatorEquationDTO> equations = new List<CalculatorEquationDTO>();
            using (UnitOfWork unitOfWork = new UnitOfWork())
            {
                foreach (var item in unitOfWork.EquationRepository.Get())
                {
                    equations.Add(new CalculatorEquationDTO
                    {
                        ID=item.ID,
                        equation=item.equation,
                        x1=item.x1,
                        x2=item.x2,
                        appID=item.appID
                    });
                }
            }
            if (equations.Count > 0)
            {
                return equations;
            }
            else
            {
                return null;
            }
        }

        public Tuple<string, bool> Save(CalculatorEquationDTO calculatorEquationDTO)
        {
            using (UnitOfWork unitOfWork = new UnitOfWork())
            {
                CalculatorEquation calculatorEquation;
                if (String.Compare(calculatorEquationDTO.x2, "") == 0)
                {
                    calculatorEquation = new CalculatorEquation
                    {
                        equation = calculatorEquationDTO.equation.Trim(),
                        x1 = calculatorEquationDTO.x1.Trim(),
                        x2 = null,
                        appID=calculatorEquationDTO.appID
                    };
                }
                else
                {
                    calculatorEquation = new CalculatorEquation
                    {
                        equation = calculatorEquationDTO.equation.Trim(),
                        x1 = calculatorEquationDTO.x1.Trim(),
                        x2 = calculatorEquationDTO.x2.Trim(),
                        appID = calculatorEquationDTO.appID
                    };
                }
                try
                {

                    unitOfWork.EquationRepository.Insert(calculatorEquation);
                    unitOfWork.Save();

                    return new Tuple<string, bool>("Уравнението и неговите корени бяха успешно запазени!", true);
                }
                catch (Exception ex)
                {
                    return new Tuple<string, bool>(ex.Message, false);
                }
            }
        }
        public Tuple<string, bool> Delete(int id)
        {
            using (UnitOfWork unitOfWork = new UnitOfWork())
            {
                try
                {
                    CalculatorEquation calculationEquation = unitOfWork.EquationRepository.Get(x => x.appID == id).Single();

                    if (calculationEquation == null)
                    {
                        return new Tuple<string, bool>("Такова уравнение не съществува!", false);
                    }
                    try
                    {
                        unitOfWork.EquationRepository.Delete(calculationEquation.ID);
                        unitOfWork.Save();

                        return new Tuple<string, bool>("Уравнение беше изтрито успешно!", true);
                    }
                    catch (Exception)
                    {
                        return new Tuple<string, bool>("Уравнението не успя да бъде изтрито!", false);
                    }
                }
                catch (InvalidOperationException)
                {
                    return new Tuple<string, bool>("Такова уравнение не съществува!", false);
                }
            }
        }
    }
}
