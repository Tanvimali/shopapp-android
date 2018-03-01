package com.shopapp.ui.checkout.view

import android.content.Context
import android.view.View
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.shopapp.gateway.entity.Customer
import com.shopapp.test.MockInstantiator
import com.shopapp.test.robolectric.ShadowTextInputLayout
import kotlinx.android.synthetic.main.view_checkout_email.view.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, shadows = [ShadowTextInputLayout::class])
class CheckoutEmailViewTest {

    private lateinit var context: Context
    private lateinit var view: CheckoutEmailView
    private lateinit var customer: Customer

    @Before
    fun setUpTest() {
        context = RuntimeEnvironment.application.baseContext
        view = CheckoutEmailView(context)
        customer = MockInstantiator.newCustomer()
    }

    @Test
    fun shouldViewBeGoneWhenCustomerNotNull() {
        assertEquals(View.VISIBLE, view.visibility)
        view.setData(customer)
        assertEquals(View.GONE, view.visibility)
        assertEquals(customer.email, view.emailInput.text.toString())
    }

    @Test
    fun shouldViewBeVisibleWhenCustomerNull() {
        assertEquals(View.VISIBLE, view.visibility)
        view.setData(null)
        assertEquals(View.VISIBLE, view.visibility)
        assertEquals("", view.emailInput.text.toString())
    }

    @Test
    fun shouldReturnEmailWhenEmailDataIsValid() {
        val testEmail = "testemail@gmail.com"
        given(customer.email).willReturn(testEmail)
        view.setData(customer)
        assertEquals(testEmail, view.getEmail())
    }

    @Test
    fun shouldReturnNullWhenEmailDataIsInvalid() {
        val testEmail = "testemail.com"
        given(customer.email).willReturn(testEmail)
        view.setData(customer)
        assertNull(view.getEmail())
    }

    @Test
    fun shouldCallOnEmailChanged() {
        val emailChangeListener: CheckoutEmailView.EmailChangeListener = mock()
        view.emailChangeListener = emailChangeListener
        view.setData(customer)
        view.emailInput.setText("testemail1@gmail.com")
        verify(emailChangeListener, times(2)).onEmailChanged()
    }
}