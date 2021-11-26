using ApplicationService.DTOs;
using ApplicationService.ManagementServices;
using BankApiService.Messages;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace BankApiService.Controllers
{
    [RoutePrefix("api/equations")]
    public class CalculatorEquationController : ApiController
    {
        private readonly CalculatorEquationManagementService service = null;
        public CalculatorEquationController()
        {
            service = new CalculatorEquationManagementService();
        }
        [HttpGet]
        [Route("")]
        public IHttpActionResult GetAllEquations()
        {
            ResponseMessage response = new ResponseMessage();
            return Json(service.GetAllEquations());
        }

        [HttpPost]
        [Route("")]
        public IHttpActionResult Save(CalculatorEquationDTO calculatorEquationDTO)
        {
            ResponseMessage response = new ResponseMessage();
            Tuple<string, bool> responseTuple = service.Save(calculatorEquationDTO);
            if (responseTuple.Item2)
            {
                response.Code = 201;
                response.Body = responseTuple.Item1;
            }
            else
            {
                response.Code = 200;
                response.Error = responseTuple.Item1;
            }
            return Json(response);
        }


        [Route("{id}")]
        [HttpDelete]
        public IHttpActionResult Delete(int id)
        {
            ResponseMessage response = new ResponseMessage();
            Tuple<string, bool> responseTuple = service.Delete(id);
            if (responseTuple.Item2)
            {
                response.Code = 200;
                response.Body = responseTuple.Item1;
            }
            else
            {
                response.Code = 200;
                response.Error = responseTuple.Item1;
            }
            return Json(response);
        }
    }
}