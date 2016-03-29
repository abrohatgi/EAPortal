    // $(document).ready(function() {
    //     $('body.homepage #textInput').css( 'color', '#b0b6b8').focus().prop('selectionStart', 0).prop('selectionEnd', 0);
    //     $("body.homepage #textInput").one("keypress", function () {
    //         jQuery(this).val('').focus().css( 'color', '#000');
    //     });
    // });

    $(document).ready(function() {
        $('body.homepage #textInput').focus();
    });          

    $(function() { 
        $( "#textInput" ).click(function() {
            $("#searchResults").addClass('visible');
            $(".whiteOverlay").addClass('visible');
            $(".searchClear").addClass('visible');
        });

        $( "#textInput" ).blur(function() {
            $("#searchResults").removeClass('visible');
            $(".whiteOverlay").removeClass('visible');
            $(".searchClear").removeClass('visible');
            // $('#textInput').val('Search keyword');
            // $('body.homepage #textInput').css( 'color', '#000')
        });

        //$('#mobileNav .nav_search a, button.searchClear').click(function() {
          //event.preventDefault();
          //$(".whiteOverlay").addClass('visible');
          //$("#textInput").addClass('focus');
          //$('#textInput').focus();
          //$('#textInput').val('');
        //);

        $( "#textInput" ).keypress(function() {
            $("#searchResults").addClass('visible');
            $(".whiteOverlay").addClass('visible');
            $(".searchClear").addClass('visible');
        });
    });


