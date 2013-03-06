/*
 * HomeScene.cpp
 *
 *  Created on: Mar 1, 2013
 *      Author: john
 */

#include "/home/john/android/cocos2d-2.0-x-2.0.4/project3/Classes/HomeScene.h"
#include "SimpleAudioEngine.h"
#include "OptionScene.h"
#include "GameScene.h"
#include <stdio.h>

using namespace cocos2d;
using namespace CocosDenshion;

HomeScene::HomeScene() {
	// TODO Auto-generated constructor stub

}

HomeScene::~HomeScene() {
	// TODO Auto-generated destructor stub
}

bool HomeScene::init()
{
	//init layer
	if ( !CCLayer::init() )	return false;

//----------------------------------------------------------------------------------------------------------------------------------------------------
//--add sprites: title, author, option, and begin
//----------------------------------------------------------------------------------------------------------------------------------------------------
	//get window size
	CCSize winSize = CCDirector::sharedDirector()->getWinSize();

	//create author, set scale, set position
	CCSprite *author = CCSprite::create("author.png");
	author->setScaleX((3*winSize.width)/(author->getContentSize().width*8));
	author->setScaleY(winSize.height/(author->getContentSize().height*8));
	author->setPosition(ccp(winSize.width/2, (5*winSize.height)/8));
	this->addChild(author);

	//create title, set scale, set position, add title
	CCSprite *title = CCSprite::create("bb.png");
	title->setScaleX(winSize.width/title->getContentSize().width);
	title->setScaleY((winSize.height)/(title->getContentSize().height*4));
	title->setPosition(ccp(winSize.width/2, (13*winSize.height)/16));
	this->addChild(title);

	//create title, set scale
	CCSprite *option = CCSprite::create("options.png");
	option->setScaleX(winSize.width/(option->getContentSize().width*4));
	option->setScaleY(winSize.height/(option->getContentSize().height*4));

	//create title, set scale
	CCSprite *begin = CCSprite::create("begin.png");
	begin->setScaleX(winSize.width/(4*begin->getContentSize().width));
	begin->setScaleY(winSize.height/(4*begin->getContentSize().height));

//----------------------------------------------------------------------------------------------------------------------------------------------------
//--add menu items
//----------------------------------------------------------------------------------------------------------------------------------------------------

	//create the two items
	CCMenuItemSprite *pOptionItem = CCMenuItemSprite::create(option, option, this, menu_selector(HomeScene::menuOptionCallback));
	if(!pOptionItem) return false;
	CCMenuItemSprite *pBeginItem = CCMenuItemSprite::create(begin, begin, this, menu_selector(HomeScene::menuBeginCallback));
	if(!pBeginItem) return false;

	//create menu
	CCMenu* pMenu = CCMenu::create(pOptionItem, pBeginItem, NULL);
	if(!pMenu) return false;

	//setup positioning
	pMenu->setPosition(ccp(9*winSize.width/16, 3*winSize.height/8));
	pMenu->alignItemsHorizontally();

	//add menu item as layer
	this->addChild(pMenu, 1);


	return true;
}

CCScene* HomeScene::scene()
{
    // 'scene' is an autorelease object
    CCScene *scene = CCScene::create();

    // 'layer' is an autorelease object
    HomeScene *layer = HomeScene::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}

void HomeScene::menuOptionCallback(CCObject* pSender)
{
    // create a scene. it's an autorelease object
    CCScene *pScene = OptionScene::scene();

    // run
    CCDirector::sharedDirector()->replaceScene(pScene);

#if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif
}

void HomeScene::menuBeginCallback(CCObject* pSender)
{
    // create a scene. it's an autorelease object
    CCScene *pScene = GameScene::scene();

    // run
    CCDirector::sharedDirector()->replaceScene(pScene);
}

