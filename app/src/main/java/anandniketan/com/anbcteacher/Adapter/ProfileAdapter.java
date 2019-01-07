package anandniketan.com.anbcteacher.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import anandniketan.com.anbcteacher.Interfacess.onEditTest;
import anandniketan.com.anbcteacher.Interfacess.onInboxRead;
import anandniketan.com.anbcteacher.Models.UserFinalArray;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;
import anandniketan.com.anbcteacher.databinding.ChangePasswordItemListBinding;
import anandniketan.com.anbcteacher.databinding.DepartmentDetailItemListBinding;
import anandniketan.com.anbcteacher.databinding.PersonaldetailItemListBinding;


public class ProfileAdapter extends RecyclerView.Adapter {

    private final static int HEADER_VIEW = 0;
    private final static int ROW_VIEW = 1;
    private final static int CONTENT_VIEW = 2;

    Context mContext;
    List<UserFinalArray> personalDetails;
    List<UserFinalArray> departmentDetails;
    List<UserFinalArray> passwordDetails;
    onEditTest onEditTest;

    PersonaldetailItemListBinding personaldetailItemListBinding;
    DepartmentDetailItemListBinding departmentdetailItemListBinding;
    ChangePasswordItemListBinding changePassowrdItemListBinding;

    String currentPasswordStr, newPasswordStr, confirmPasswordStr,oldPassword = "";
    private ArrayList<String> editpasswordDetail;
    private ArrayList<String> layout;

    public ProfileAdapter(Context mContext, List<UserFinalArray> personalDetails,
                          List<UserFinalArray> departmentDetails, List<UserFinalArray> passwordDetails, onEditTest onEditTest) {
        this.mContext = mContext;
        this.personalDetails = personalDetails;
        this.departmentDetails = departmentDetails;
        this.passwordDetails = passwordDetails;
        this.onEditTest = onEditTest;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == HEADER_VIEW) {
            personaldetailItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.personaldetail_item_list, parent, false);
            view = personaldetailItemListBinding.getRoot();
            return new PersonalDetailCard(view);
        }

        if (viewType == ROW_VIEW) {
            departmentdetailItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.department_detail_item_list, parent, false);
            view = departmentdetailItemListBinding.getRoot();
            return new DepartmentDetailCard(view);
        }
        if (viewType == CONTENT_VIEW) {
            changePassowrdItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.change_password_item_list, parent, false);
            view = changePassowrdItemListBinding.getRoot();
            return new ChangePasswordDetailCard(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PersonalDetailCard) {
            personaldetailItemListBinding.usernameTxt.setText(personalDetails.get(position).getEmpName() + " " +
                    "(" + personalDetails.get(position).getEmpCode() + ")");
            personaldetailItemListBinding.userdobTxt.setText(personalDetails.get(position).getDob());
            personaldetailItemListBinding.usermobileTxt.setText(personalDetails.get(position).getMobile());
            personaldetailItemListBinding.useremailTxt.setText(personalDetails.get(position).getEmailID());
            personaldetailItemListBinding.useraddressTxt.setText(personalDetails.get(position).getAddress() + "," +
                    personalDetails.get(position).getCity());
        }

        if (holder instanceof DepartmentDetailCard) {
            departmentdetailItemListBinding.userdepartmentnameTxt.setText(departmentDetails.get(position - personalDetails.size()).getDepratment());
            departmentdetailItemListBinding.userdesignationTxt.setText(departmentDetails.get(position - personalDetails.size()).getDesignation());
        }
        if (holder instanceof ChangePasswordDetailCard) {
            changePassowrdItemListBinding.txtUserName.setText(passwordDetails.get(position - personalDetails.size() - departmentDetails.size()).getUserName());
            changePassowrdItemListBinding.txtcurrentPassword.setText(passwordDetails.get(position - personalDetails.size() - departmentDetails.size()).getPassword());

            changePassowrdItemListBinding.changeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (changePassowrdItemListBinding.changeBtn.getText().toString().equalsIgnoreCase("CHANGE")) {
                        changePassowrdItemListBinding.changeBtn.setText("CANCEL");
                        changePassowrdItemListBinding.passwordLinear.setVisibility(View.GONE);
                        changePassowrdItemListBinding.currentPasswordLinear.setVisibility(View.VISIBLE);
                        changePassowrdItemListBinding.newPasswordLinear.setVisibility(View.VISIBLE);
                        changePassowrdItemListBinding.confirmPasswordLinear.setVisibility(View.VISIBLE);
                        changePassowrdItemListBinding.btnUpdate.setVisibility(View.VISIBLE);
                    } else if (changePassowrdItemListBinding.changeBtn.getText().toString().equalsIgnoreCase("CANCEL")) {
                        changePassowrdItemListBinding.changeBtn.setText("CHANGE");
                        changePassowrdItemListBinding.passwordLinear.setVisibility(View.VISIBLE);
                        changePassowrdItemListBinding.currentPasswordLinear.setVisibility(View.GONE);
                        changePassowrdItemListBinding.newPasswordLinear.setVisibility(View.GONE);
                        changePassowrdItemListBinding.confirmPasswordLinear.setVisibility(View.GONE);
                        changePassowrdItemListBinding.btnUpdate.setVisibility(View.GONE);

                    }
                }
            });
            changePassowrdItemListBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oldPassword = Utility.getPref(mContext, "pwd");
                    currentPasswordStr = changePassowrdItemListBinding.edtcurrentPassword.getText().toString().trim();
                    newPasswordStr = changePassowrdItemListBinding.edtnewPassword.getText().toString();
                    confirmPasswordStr = changePassowrdItemListBinding.edtconfirmPassword.getText().toString();


                    if (oldPassword.trim().equalsIgnoreCase(currentPasswordStr)) {
                        if (!newPasswordStr.equalsIgnoreCase("")) {
                            if (newPasswordStr.length() >= 3 & newPasswordStr.length() <= 13) {
                                if (newPasswordStr.equalsIgnoreCase(confirmPasswordStr)) {
                                    editpasswordDetail = new ArrayList<>();
                                    editpasswordDetail.add(newPasswordStr);//+"|"+passwordDetails.get(position - personalDetails.size() - departmentDetails.size()).getStaffID());
                                    onEditTest.getEditTest();
                                } else {
                                    Utility.ping(mContext, "Confirm password does't match to new password");
                                }

                            } else {
                                Utility.ping(mContext, "Password must be 3 to 13 character");
                            }
                        } else {
                            Utility.ping(mContext, "Please enter password");
                        }
                    }else {
                        Utility.ping(mContext, "Current password does't match");
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position < personalDetails.size()) {
            return HEADER_VIEW;
        }
        if (position - personalDetails.size() < departmentDetails.size()) {//- descriptionviewarray.size()
            return ROW_VIEW;
        }
        if (position - personalDetails.size() - departmentDetails.size() < passwordDetails.size()) {//- descriptionviewarray.size()
            return CONTENT_VIEW;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return personalDetails.size() + departmentDetails.size() + passwordDetails.size();// + descriptionviewarray.size()
    }

    public ArrayList<String> getEditPaswordDetail() {
        return editpasswordDetail;
    }

    public class PersonalDetailCard extends RecyclerView.ViewHolder {

        public PersonalDetailCard(View view) {
            super(view);
        }
    }

    public class DepartmentDetailCard extends RecyclerView.ViewHolder {

        public DepartmentDetailCard(View itemView) {
            super(itemView);
        }
    }

    public class ChangePasswordDetailCard extends RecyclerView.ViewHolder {

        public ChangePasswordDetailCard(View view) {
            super(view);

        }
    }
}


